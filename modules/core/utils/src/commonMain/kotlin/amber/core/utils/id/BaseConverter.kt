package amber.core.utils.id

import kotlin.math.ceil
import kotlin.math.ln
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class BaseConverter(base: String) {
    private val targetBaseSize = base.length

    private val alphabet = base.encodeToByteArray()
    private val alphabetLookup = createLookupTable(alphabet)

    fun encode(data: ByteArray): ByteArray {
        val targetIndices = convert(data, SOURCE_BASE_SIZE, targetBaseSize)
        return targetIndices.translate(alphabet)
    }

    fun decode(encoded: ByteArray): ByteArray {
        require(isEncodingCorrect(encoded)) {
            "Input ${encoded.decodeToString()} is not encoded correctly"
        }
        val indices: ByteArray = encoded.translate(alphabetLookup)
        return convert(indices, targetBaseSize, SOURCE_BASE_SIZE)
    }

    fun canBeDecoded(data: ByteArray): Boolean = isEncodingCorrect(data)

    /**
     * Converts a byte array from a source base to a target base using the alphabet.
     */
    private fun convert(message: ByteArray, sourceBase: Int, targetBase: Int): ByteArray {
        /**
         * This algorithm is inspired by: http://codegolf.stackexchange.com/a/21672
         */
        val estimatedLength = estimateOutputLength(message.size, sourceBase, targetBase)
        val out = ByteArrayBuilder(estimatedLength)
        var source = message
        while (source.isNotEmpty()) {
            val quotient = ByteArrayBuilder(source.size)
            var remainder = 0
            for (i in source.indices) {
                val nextByte = source[i].toInt() and HEX_255
                val accumulator: Int = nextByte + remainder * sourceBase
                val digit = (accumulator - accumulator % targetBase) / targetBase
                remainder = accumulator % targetBase
                if (quotient.size > 0 || digit > 0) {
                    quotient.add(digit.toByte())
                }
            }
            out.add(remainder.toByte())
            source = quotient.toByteArray()
        }

        padWithZeroes(out, message)
        return out.toByteArray().apply { reverse() }
    }

    /**
     * Uses the elements of a byte array as indices to a dictionary and returns the corresponding values
     * in form of a byte array.
     */
    private fun ByteArray.translate(dictionary: ByteArray): ByteArray {
        return ByteArray(size) { index ->
            dictionary[get(index).toInt()]
        }
    }

    /**
     * Estimates the length of the output in bytes.
     */
    private fun estimateOutputLength(inputLength: Int, sourceBase: Int, targetBase: Int): Int {
        return ceil(ln(sourceBase.toDouble()) / ln(targetBase.toDouble()) * inputLength).toInt()
    }

    private fun isEncodingCorrect(encoded: ByteArray): Boolean {
        return encoded.isNotEmpty() && encoded.all { alphabet.contains(it) }
    }

    private fun createLookupTable(alphabet: ByteArray): ByteArray {
        val lookup = ByteArray(SOURCE_BASE_SIZE)
        for (i in alphabet.indices) {
            lookup[alphabet[i].toInt()] = i.toByte()
        }
        return lookup
    }

    private fun padWithZeroes(out: ByteArrayBuilder, message: ByteArray) {
        var i = 0
        val zero: Byte = 0
        while (i < message.size - 1 && message[i] == zero) {
            out.add(0)
            i++
        }
    }
}

fun BaseConverter.encodeToString(data: ByteArray): String {
    return encode(data).decodeToString()
}

fun BaseConverter.decodeFromString(encoded: String): ByteArray {
    return decode(encoded.encodeToByteArray())
}

fun BaseConverter.canBeDecoded(encoded: String): Boolean = canBeDecoded(encoded.encodeToByteArray())

fun BaseConverter.encodeLong(long: Long): ByteArray {
    val input = ByteArray(Long.SIZE_BYTES) { i ->
        (long shr (Long.SIZE_BYTES - 1 - i) * Byte.SIZE_BITS).toByte()
    }
    return encode(input)
}

fun BaseConverter.decodeToLong(encoded: ByteArray): Long {
    val decoded = decode(encoded)
    var result = 0L
    for (byte in decoded) {
        result = (result shl Byte.SIZE_BITS) or (byte.toLong() and 0xFFL)
    }
    return result
}

fun BaseConverter.encodeLongToString(long: Long): String {
    return encodeLong(long).decodeToString()
}

fun BaseConverter.decodeStringToLong(encoded: String): Long {
    return decodeToLong(encoded.encodeToByteArray())
}

@OptIn(ExperimentalUuidApi::class)
fun BaseConverter.encodeUuid(uuid: Uuid): ByteArray {
    return encode(uuid.toByteArray())
}

@OptIn(ExperimentalUuidApi::class)
fun BaseConverter.decodeToUuid(encoded: ByteArray): Uuid {
    return Uuid.fromByteArray(decode(encoded))
}

@OptIn(ExperimentalUuidApi::class)
fun BaseConverter.encodeUuidToString(uuid: Uuid): String {
    return encodeUuid(uuid).decodeToString()
}

@OptIn(ExperimentalUuidApi::class)
fun BaseConverter.decodeStringToUuid(encoded: String): Uuid {
    return decodeToUuid(encoded.encodeToByteArray())
}

private class ByteArrayBuilder(initialCapacity: Int) {
    private var data = ByteArray(initialCapacity)
    var size = 0
        private set

    fun add(byte: Byte) {
        if (size == data.size) {
            data = data.copyOf(maxOf(data.size * 2, 1))
        }
        data[size++] = byte
    }

    fun toByteArray(): ByteArray = data.copyOf(size)
}

const val SOURCE_BASE_SIZE = 256
const val HEX_255 = 0xff
