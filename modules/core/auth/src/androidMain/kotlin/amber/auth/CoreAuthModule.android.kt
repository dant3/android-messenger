package amber.auth

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val CoreAuthStorageModule: Module = module {
    singleOf(::AndroidSecureStorage).bind(SecureStorage::class)
}
