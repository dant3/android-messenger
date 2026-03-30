package amber.feature.onboarding.core.usecase.dummy

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.result.shouldBeSuccess

class DummyForgotPasswordUseCaseTest : FunSpec({
    test("requestPasswordReset returns success") {
        val useCase = DummyForgotPasswordUseCase()
        useCase.requestPasswordReset("test@example.com").shouldBeSuccess()
    }
})
