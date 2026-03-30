package amber.feature.onboarding.core.usecase.dummy

import amber.auth.AuthTokenStorage
import amber.feature.onboarding.core.usecase.SetPasswordUseCase
import kotlinx.coroutines.delay

class DummySetPasswordUseCase(
    private val authTokenStorage: AuthTokenStorage,
) : SetPasswordUseCase {
    override suspend fun setPassword(email: String, password: String): Result<Unit> {
        delay(500)
        authTokenStorage.storeDummyAuth(email)
        return Result.success(Unit)
    }
}
