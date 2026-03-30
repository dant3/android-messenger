package amber.feature.onboarding.core.usecase.dummy

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.result.shouldBeSuccess

class DummyConfirmEmailUseCaseTest : FunSpec({
    test("confirmEmail returns success") {
        val useCase = DummyConfirmEmailUseCase()
        useCase.confirmEmail("test@example.com", "123456").shouldBeSuccess()
    }
})
