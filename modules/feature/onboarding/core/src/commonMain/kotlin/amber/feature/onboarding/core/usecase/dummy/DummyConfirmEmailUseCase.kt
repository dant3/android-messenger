package amber.feature.onboarding.core.usecase.dummy

import amber.feature.onboarding.core.usecase.ConfirmEmailUseCase
import kotlinx.coroutines.delay

class DummyConfirmEmailUseCase : ConfirmEmailUseCase {
    override suspend fun confirmEmail(email: String, code: String): Result<Unit> {
        delay(500)
        return Result.success(Unit)
    }
}
