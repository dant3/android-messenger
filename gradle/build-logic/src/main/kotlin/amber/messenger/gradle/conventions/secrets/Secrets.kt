package amber.messenger.gradle.conventions.secrets

import java.io.File

data class Secrets(
    val googlePlayServiceAccountCredentialsFile: File,
    val rustoreCredentialsFile: File,
    val huaweiCredentialsFile: File,
) {
    companion object {
        private val DEFAULTS = Secrets(
            googlePlayServiceAccountCredentialsFile = File("app/amber-messenger-service-account.json"),
            rustoreCredentialsFile = File("app/rustore-credentials.json"),
            huaweiCredentialsFile = File("app/huawei-credentials.json"),
        )

        fun loadFromEnv(): Secrets {
            val env = System.getenv()

            return Secrets(
                googlePlayServiceAccountCredentialsFile =
                    env["GOOGLE_PLAY_SERVICE_ACCOUNT_CREDENTIALS"]?.let(::File) ?: DEFAULTS.googlePlayServiceAccountCredentialsFile,
                rustoreCredentialsFile =
                    env["RUSTORE_CREDENTIALS"]?.let(::File) ?: DEFAULTS.rustoreCredentialsFile,
                huaweiCredentialsFile =
                    env["HUAWEI_CREDENTIALS"]?.let(::File) ?: DEFAULTS.huaweiCredentialsFile,
            )
        }
    }
}
