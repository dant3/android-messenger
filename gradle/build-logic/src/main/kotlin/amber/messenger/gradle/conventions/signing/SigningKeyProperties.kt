package amber.messenger.gradle.conventions.signing

import java.io.File
import java.util.Properties

data class SigningKeyProperties(
    val storePassword: String?,
    val keyAlias: String?,
    val keyPassword: String?,
) {
    companion object {
        fun load(propertiesFile: File): SigningKeyProperties {
            val properties = loadProperties(propertiesFile)
            return SigningKeyProperties(
                storePassword = properties.get("passwd"),
                keyAlias = properties.get("key.alias"),
                keyPassword = properties.get("key.passwd"),
            )
        }

        private fun loadProperties(propertiesFile: File): Map<String, String> {
            try {
                val properties = Properties().apply {
                    propertiesFile.inputStream().use {
                        this.load(it)
                    }
                }
                return properties.entries.associateBy(
                    keySelector = { it.key.toString() },
                    valueTransform = { it.value.toString() },
                )
            } catch (ignored: Throwable) {
                return emptyMap()
            }
        }
    }
}
