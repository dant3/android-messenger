package amber.client.impl

import amber.client.api.ApiClient
import io.ktor.client.engine.darwin.Darwin
import kotlinx.serialization.json.Json
import org.koin.core.module.Module
import org.koin.dsl.module

actual val CoreClientModule: Module = module {
    single {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    single { createHttpClient(Darwin, get()) }
    single<ApiClient> { KtorApiClient(get(), get()) }
}
