package amber.feature.onboarding.core.usecase.dummy

import amber.feature.onboarding.core.usecase.RegisterUseCase
import kotlinx.coroutines.delay

class DummyRegisterUseCase : RegisterUseCase {
    override suspend fun register(email: String): Result<Unit> {
        delay(500)
        return Result.success(Unit)
    }
}
