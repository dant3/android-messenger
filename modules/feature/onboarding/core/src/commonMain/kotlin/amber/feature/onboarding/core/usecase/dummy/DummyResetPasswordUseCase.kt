package amber.feature.onboarding.core.usecase.dummy

import amber.auth.AuthTokenStorage
import amber.feature.onboarding.core.usecase.ResetPasswordUseCase
import kotlinx.coroutines.delay

class DummyResetPasswordUseCase(
    private val authTokenStorage: AuthTokenStorage,
) : ResetPasswordUseCase {
    override suspend fun resetPassword(email: String, code: String, newPassword: String): Result<Unit> {
        delay(500)
        authTokenStorage.storeDummyAuth(email)
        return Result.success(Unit)
    }
}
