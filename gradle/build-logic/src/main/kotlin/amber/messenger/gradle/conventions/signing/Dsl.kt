package amber.messenger.gradle.conventions.signing

import com.android.build.api.dsl.ApkSigningConfig
import java.io.File
import org.gradle.api.NamedDomainObjectContainer

fun NamedDomainObjectContainer<out ApkSigningConfig>.registerSigningKey(
    name: String,
    storeFile: File,
    propertiesFile: File,
) {
    val key = SigningKeyProperties.load(propertiesFile)

    with(maybeCreate(name)) {
        this.storeFile = storeFile
        this.storePassword = key.storePassword
        this.keyAlias = key.keyAlias
        this.keyPassword = key.keyPassword
    }
}
