package amber.auth

import org.koin.core.module.Module
import org.koin.dsl.module

actual val CoreAuthStorageModule: Module = module {
    single<SecureStorage> { JvmSecureStorage() }
}
