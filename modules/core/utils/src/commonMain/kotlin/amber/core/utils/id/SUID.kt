package amber.core.utils.id

import kotlin.jvm.JvmInline
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@JvmInline
value class SUID private constructor(private val string: String) {
    override fun toString(): String = string

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        fun randomSUID(): SUID {
            var base62Uuid = Base62.encodeUuidToString(Uuid.random())
            if (base62Uuid.length < 22) {
                base62Uuid = base62Uuid.padStart(22, '0')
            }
            return SUID(base62Uuid)
        }

        @OptIn(ExperimentalUuidApi::class)
        fun fromString(string: String): SUID {
            // we perform conversion purely to check correctness
            return fromUUID(Base62.decodeStringToUuid(string))
        }

        @OptIn(ExperimentalUuidApi::class)
        internal fun fromUUID(uuid: Uuid): SUID = SUID(Base62.encodeUuidToString(uuid))

        @OptIn(ExperimentalUuidApi::class)
        internal fun toUUID(suid: SUID): Uuid = Base62.decodeStringToUuid(suid.string)
    }
}
