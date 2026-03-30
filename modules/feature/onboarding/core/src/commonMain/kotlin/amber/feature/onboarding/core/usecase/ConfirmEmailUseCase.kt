package amber.feature.onboarding.core.usecase

fun interface ConfirmEmailUseCase {
    suspend fun confirmEmail(email: String, code: String): Result<Unit>
}
