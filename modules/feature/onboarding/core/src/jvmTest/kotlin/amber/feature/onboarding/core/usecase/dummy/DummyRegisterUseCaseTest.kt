package amber.feature.onboarding.core.usecase.dummy

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.result.shouldBeSuccess

class DummyRegisterUseCaseTest : FunSpec({
    test("register returns success") {
        val useCase = DummyRegisterUseCase()
        useCase.register("test@example.com").shouldBeSuccess()
    }
})
