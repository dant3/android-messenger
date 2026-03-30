package amber.feature.onboarding.core.usecase

fun interface ResetPasswordUseCase {
    suspend fun resetPassword(email: String, code: String, newPassword: String): Result<Unit>
}
