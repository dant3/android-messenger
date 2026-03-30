package amber.feature.onboarding.core.usecase.dummy

import amber.feature.onboarding.core.usecase.ForgotPasswordUseCase
import kotlinx.coroutines.delay

class DummyForgotPasswordUseCase : ForgotPasswordUseCase {
    override suspend fun requestPasswordReset(email: String): Result<Unit> {
        delay(500)
        return Result.success(Unit)
    }
}
