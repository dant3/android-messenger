package amber.client.impl

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(
    engineFactory: HttpClientEngineFactory<*>,
    json: Json,
): HttpClient = HttpClient(engineFactory) {
    install(ContentNegotiation) {
        json(json)
    }
    install(Logging) {
        level = LogLevel.BODY
    }
}
