package amber.auth

import java.nio.file.Files
import java.nio.file.Path
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import kotlin.io.path.Path
import kotlin.io.path.exists
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream
import kotlin.io.path.readBytes
import kotlin.io.path.writeBytes

internal class JvmSecureStorage(
    private val storageDir: Path = Path(System.getProperty("user.home"), ".amber-messenger"),
) : SecureStorage {

    private val keystorePath = storageDir.resolve("auth.keystore")
    private val dataPath = storageDir.resolve("auth.dat")
    private val keystore: KeyStore = KeyStore.getInstance("PKCS12")

    init {
        Files.createDirectories(storageDir)
        if (keystorePath.exists()) {
            keystorePath.inputStream().use { keystore.load(it, KEYSTORE_PASSWORD) }
        } else {
            keystore.load(null, KEYSTORE_PASSWORD)
        }
    }

    override fun getString(key: String): String? {
        val entries = loadEntries()
        return entries[key]
    }

    override fun putString(key: String, value: String) {
        val entries = loadEntries().toMutableMap()
        entries[key] = value
        saveEntries(entries)
    }

    override fun remove(key: String) {
        val entries = loadEntries().toMutableMap()
        entries.remove(key)
        saveEntries(entries)
    }

    override fun clear() {
        saveEntries(emptyMap())
    }

    private fun getOrCreateKey(): SecretKey {
        val entry = keystore.getEntry(KEY_ALIAS, KeyStore.PasswordProtection(KEYSTORE_PASSWORD))
        if (entry is KeyStore.SecretKeyEntry) {
            return entry.secretKey
        }
        val keyGen = KeyGenerator.getInstance("AES")
        keyGen.init(AES_KEY_SIZE)
        val secretKey = keyGen.generateKey()
        keystore.setEntry(
            KEY_ALIAS,
            KeyStore.SecretKeyEntry(secretKey),
            KeyStore.PasswordProtection(KEYSTORE_PASSWORD),
        )
        keystorePath.outputStream().use { keystore.store(it, KEYSTORE_PASSWORD) }
        return secretKey
    }

    private fun loadEntries(): Map<String, String> {
        if (!dataPath.exists()) return emptyMap()
        val raw = dataPath.readBytes()
        if (raw.size < GCM_IV_LENGTH) return emptyMap()
        val iv = raw.copyOfRange(0, GCM_IV_LENGTH)
        val ciphertext = raw.copyOfRange(GCM_IV_LENGTH, raw.size)
        val cipher = Cipher.getInstance(AES_GCM_TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, getOrCreateKey(), GCMParameterSpec(GCM_TAG_LENGTH, iv))
        val plaintext = cipher.doFinal(ciphertext).decodeToString()
        return plaintext.split("\n").filter { it.contains("=") }.associate { line ->
            val eqIndex = line.indexOf('=')
            line.substring(0, eqIndex) to line.substring(eqIndex + 1)
        }
    }

    private fun saveEntries(entries: Map<String, String>) {
        val plaintext = entries.entries.joinToString("\n") { "${it.key}=${it.value}" }
        val iv = ByteArray(GCM_IV_LENGTH).also { SecureRandom().nextBytes(it) }
        val cipher = Cipher.getInstance(AES_GCM_TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, getOrCreateKey(), GCMParameterSpec(GCM_TAG_LENGTH, iv))
        val ciphertext = cipher.doFinal(plaintext.toByteArray())
        dataPath.writeBytes(iv + ciphertext)
    }

    private companion object {
        const val KEY_ALIAS = "amber_auth_key"
        val KEYSTORE_PASSWORD = "amber-auth-ks".toCharArray()
        const val AES_KEY_SIZE = 256
        const val GCM_IV_LENGTH = 12
        const val GCM_TAG_LENGTH = 128
        const val AES_GCM_TRANSFORMATION = "AES/GCM/NoPadding"
    }
}
