package amber.feature.onboarding.core.usecase.dummy

import amber.auth.AuthTokenStorage
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.result.shouldBeSuccess

class DummyLoginUseCaseTest : FunSpec({
    test("login stores auth and returns success") {
        val authTokenStorage = mock<AuthTokenStorage> {
            every { storeTokens(any()) } returns Unit
            every { storeProfile(any()) } returns Unit
        }
        val useCase = DummyLoginUseCase(authTokenStorage)

        useCase.login("test@example.com", "password123").shouldBeSuccess()

        verify {
            authTokenStorage.storeTokens(any())
            authTokenStorage.storeProfile(any())
        }
    }
})
