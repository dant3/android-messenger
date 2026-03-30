package amber.feature.onboarding.core.usecase

fun interface LoginUseCase {
    suspend fun login(email: String, password: String): Result<Unit>
}
