package amber.auth

import org.koin.core.module.Module
import org.koin.dsl.module

actual val CoreAuthModule: Module = module {
    single<SecureStorage> { IosSecureStorage() }
    single { AuthManager(secureStorage = get()) }
    single<AuthTokenStorage> { get<AuthManager>() }
    single<AuthStateProvider> { get<AuthManager>() }
}
