package amber.client.impl

import amber.client.api.ApiClient
import amber.client.api.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

class KtorApiClient(
    private val httpClient: HttpClient,
    private val json: Json,
) : ApiClient {
    override suspend fun <T> get(
        url: String,
        responseDeserializer: DeserializationStrategy<T>,
    ): ApiResponse<T> = executeRequest(responseDeserializer) {
        httpClient.get(url)
    }

    override suspend fun <T, B> post(
        url: String,
        body: B,
        bodySerializer: SerializationStrategy<B>,
        responseDeserializer: DeserializationStrategy<T>,
    ): ApiResponse<T> = executeRequest(responseDeserializer) {
        httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(bodySerializer, body))
        }
    }

    override suspend fun <T, B> put(
        url: String,
        body: B,
        bodySerializer: SerializationStrategy<B>,
        responseDeserializer: DeserializationStrategy<T>,
    ): ApiResponse<T> = executeRequest(responseDeserializer) {
        httpClient.put(url) {
            contentType(ContentType.Application.Json)
            setBody(json.encodeToString(bodySerializer, body))
        }
    }

    override suspend fun <T> delete(
        url: String,
        responseDeserializer: DeserializationStrategy<T>,
    ): ApiResponse<T> = executeRequest(responseDeserializer) {
        httpClient.delete(url)
    }

    private suspend fun <T> executeRequest(
        deserializer: DeserializationStrategy<T>,
        block: suspend () -> HttpResponse,
    ): ApiResponse<T> = try {
        val response = block()
        val statusCode = response.status.value
        when {
            response.status.isSuccess() -> {
                val bodyText = response.bodyAsText()
                val data = json.decodeFromString(deserializer, bodyText)
                ApiResponse.Success(data)
            }
            statusCode in 400..499 -> ApiResponse.Error.HttpError(statusCode, response.bodyAsText())
            else -> ApiResponse.Error.InternalError(statusCode, response.bodyAsText())
        }
    } catch (e: Exception) {
        ApiResponse.Error.NetworkError(e)
    }
}
