package amber.client.api

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy

interface ApiClient {
    suspend fun <T> get(
        url: String,
        responseDeserializer: DeserializationStrategy<T>,
    ): ApiResponse<T>

    suspend fun <T, B> post(
        url: String,
        body: B,
        bodySerializer: SerializationStrategy<B>,
        responseDeserializer: DeserializationStrategy<T>,
    ): ApiResponse<T>

    suspend fun <T, B> put(
        url: String,
        body: B,
        bodySerializer: SerializationStrategy<B>,
        responseDeserializer: DeserializationStrategy<T>,
    ): ApiResponse<T>

    suspend fun <T> delete(
        url: String,
        responseDeserializer: DeserializationStrategy<T>,
    ): ApiResponse<T>
}
