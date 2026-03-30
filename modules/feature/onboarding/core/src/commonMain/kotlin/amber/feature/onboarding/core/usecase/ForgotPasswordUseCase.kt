package amber.feature.onboarding.core.usecase

fun interface ForgotPasswordUseCase {
    suspend fun requestPasswordReset(email: String): Result<Unit>
}
