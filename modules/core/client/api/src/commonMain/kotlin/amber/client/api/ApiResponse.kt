package amber.client.api

sealed class ApiResponse<out T> {
    data class Success<T>(val data: T) : ApiResponse<T>()

    sealed class Error : ApiResponse<Nothing>() {
        data class HttpError(val code: Int, val message: String) : Error()
        data class InternalError(val code: Int, val message: String) : Error()
        data class NetworkError(val cause: Throwable) : Error()
    }
}
