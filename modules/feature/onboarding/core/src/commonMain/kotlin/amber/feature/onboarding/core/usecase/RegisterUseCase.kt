package amber.feature.onboarding.core.usecase

fun interface RegisterUseCase {
    suspend fun register(email: String): Result<Unit>
}
