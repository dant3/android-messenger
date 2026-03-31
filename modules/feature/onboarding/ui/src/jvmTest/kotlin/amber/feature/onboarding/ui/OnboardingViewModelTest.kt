package amber.feature.onboarding.ui

import amber.feature.onboarding.core.usecase.ConfirmEmailUseCase
import amber.feature.onboarding.core.usecase.ForgotPasswordUseCase
import amber.feature.onboarding.core.usecase.LoginUseCase
import amber.feature.onboarding.core.usecase.RegisterUseCase
import amber.feature.onboarding.core.usecase.ResetPasswordUseCase
import amber.feature.onboarding.core.usecase.SetPasswordUseCase
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class OnboardingViewModelTest : FunSpec({
    val testDispatcher = StandardTestDispatcher()

    val registerUseCase = mock<RegisterUseCase>()
    val confirmEmailUseCase = mock<ConfirmEmailUseCase>()
    val setPasswordUseCase = mock<SetPasswordUseCase>()
    val loginUseCase = mock<LoginUseCase>()
    val forgotPasswordUseCase = mock<ForgotPasswordUseCase>()
    val resetPasswordUseCase = mock<ResetPasswordUseCase>()

    lateinit var viewModel: OnboardingViewModel

    beforeEach {
        Dispatchers.setMain(testDispatcher)
        viewModel = OnboardingViewModel(
            registerUseCase = registerUseCase,
            confirmEmailUseCase = confirmEmailUseCase,
            setPasswordUseCase = setPasswordUseCase,
            loginUseCase = loginUseCase,
            forgotPasswordUseCase = forgotPasswordUseCase,
            resetPasswordUseCase = resetPasswordUseCase,
        )
    }

    afterEach {
        Dispatchers.resetMain()
    }

    test("initial state has empty fields") {
        val state = viewModel.state.value
        state.email shouldBe ""
        state.password shouldBe ""
        state.confirmationCode shouldBe ""
        state.newPassword shouldBe ""
        state.isLoading shouldBe false
        state.error shouldBe null
        state.completed shouldBe false
    }

    test("updateEmail updates email in state") {
        viewModel.updateEmail("test@example.com")
        viewModel.state.value.email shouldBe "test@example.com"
    }

    test("updatePassword updates password in state") {
        viewModel.updatePassword("secret123")
        viewModel.state.value.password shouldBe "secret123"
    }

    test("updateConfirmationCode updates code in state") {
        viewModel.updateConfirmationCode("123456")
        viewModel.state.value.confirmationCode shouldBe "123456"
    }

    test("updateNewPassword updates newPassword in state") {
        viewModel.updateNewPassword("newSecret456")
        viewModel.state.value.newPassword shouldBe "newSecret456"
    }

    test("clearError clears error") {
        runTest(testDispatcher) {
            everySuspend { registerUseCase.register("") } returns Result.failure(Exception("fail"))
            viewModel.register {}
            advanceUntilIdle()
            viewModel.state.value.error shouldBe "fail"

            viewModel.clearError()
            viewModel.state.value.error shouldBe null
        }
    }

    test("register success invokes callback") {
        runTest(testDispatcher) {
            everySuspend { registerUseCase.register("test@example.com") } returns Result.success(Unit)
            viewModel.updateEmail("test@example.com")

            var called = false
            viewModel.register { called = true }
            advanceUntilIdle()

            called shouldBe true
            viewModel.state.value.isLoading shouldBe false
            viewModel.state.value.error shouldBe null
        }
    }

    test("register failure sets error") {
        runTest(testDispatcher) {
            everySuspend { registerUseCase.register("bad@example.com") } returns
                Result.failure(Exception("Registration failed"))
            viewModel.updateEmail("bad@example.com")

            var called = false
            viewModel.register { called = true }
            advanceUntilIdle()

            called shouldBe false
            viewModel.state.value.error shouldContain "Registration failed"
            viewModel.state.value.isLoading shouldBe false
        }
    }

    test("login success invokes callback") {
        runTest(testDispatcher) {
            everySuspend { loginUseCase.login("user@example.com", "pass") } returns Result.success(Unit)
            viewModel.updateEmail("user@example.com")
            viewModel.updatePassword("pass")

            var called = false
            viewModel.login { called = true }
            advanceUntilIdle()

            called shouldBe true
            viewModel.state.value.isLoading shouldBe false
        }
    }

    test("login failure sets error") {
        runTest(testDispatcher) {
            everySuspend { loginUseCase.login("user@example.com", "wrong") } returns
                Result.failure(Exception("Invalid credentials"))
            viewModel.updateEmail("user@example.com")
            viewModel.updatePassword("wrong")

            viewModel.login {}
            advanceUntilIdle()

            viewModel.state.value.error shouldContain "Invalid credentials"
        }
    }

    test("confirmEmail success invokes callback") {
        runTest(testDispatcher) {
            everySuspend { confirmEmailUseCase.confirmEmail("a@b.com", "111111") } returns Result.success(Unit)
            viewModel.updateConfirmationCode("111111")

            var called = false
            viewModel.confirmEmail("a@b.com") { called = true }
            advanceUntilIdle()

            called shouldBe true
        }
    }

    test("setPassword success invokes callback") {
        runTest(testDispatcher) {
            everySuspend { setPasswordUseCase.setPassword("a@b.com", "mypass") } returns Result.success(Unit)
            viewModel.updatePassword("mypass")

            var called = false
            viewModel.setPassword("a@b.com") { called = true }
            advanceUntilIdle()

            called shouldBe true
        }
    }

    test("forgotPassword success invokes callback") {
        runTest(testDispatcher) {
            everySuspend { forgotPasswordUseCase.requestPasswordReset("a@b.com") } returns Result.success(Unit)
            viewModel.updateEmail("a@b.com")

            var called = false
            viewModel.forgotPassword { called = true }
            advanceUntilIdle()

            called shouldBe true
        }
    }

    test("resetPassword success invokes callback") {
        runTest(testDispatcher) {
            everySuspend { resetPasswordUseCase.resetPassword("a@b.com", "999999", "newpass") } returns
                Result.success(Unit)
            viewModel.updateConfirmationCode("999999")
            viewModel.updateNewPassword("newpass")

            var called = false
            viewModel.resetPassword("a@b.com") { called = true }
            advanceUntilIdle()

            called shouldBe true
        }
    }
})
