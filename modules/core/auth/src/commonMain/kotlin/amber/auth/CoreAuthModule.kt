package amber.auth

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.binds
import org.koin.dsl.module

internal expect val CoreAuthStorageModule: Module

val CoreAuthModule: Module = module {
    includes(CoreAuthStorageModule)
    singleOf(::AuthManager)
        .binds(arrayOf(AuthTokenStorage::class, AuthStateProvider::class))
}

