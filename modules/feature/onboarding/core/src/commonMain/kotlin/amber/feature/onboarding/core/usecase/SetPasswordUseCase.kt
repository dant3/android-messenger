package amber.feature.onboarding.core.usecase

fun interface SetPasswordUseCase {
    suspend fun setPassword(email: String, password: String): Result<Unit>
}
