package amber.feature.onboarding.core.usecase.dummy

import amber.auth.AuthTokenStorage
import amber.feature.onboarding.core.usecase.LoginUseCase
import kotlinx.coroutines.delay

class DummyLoginUseCase(
    private val authTokenStorage: AuthTokenStorage,
) : LoginUseCase {
    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(500)
        authTokenStorage.storeDummyAuth(email)
        return Result.success(Unit)
    }
}
