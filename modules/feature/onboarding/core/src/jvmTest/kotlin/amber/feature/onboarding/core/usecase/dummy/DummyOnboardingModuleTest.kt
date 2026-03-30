package amber.feature.onboarding.core.usecase.dummy

import amber.auth.CoreAuthModule
import amber.feature.onboarding.core.usecase.ConfirmEmailUseCase
import amber.feature.onboarding.core.usecase.ForgotPasswordUseCase
import amber.feature.onboarding.core.usecase.LoginUseCase
import amber.feature.onboarding.core.usecase.RegisterUseCase
import amber.feature.onboarding.core.usecase.ResetPasswordUseCase
import amber.feature.onboarding.core.usecase.SetPasswordUseCase
import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.types.shouldBeInstanceOf
import org.koin.test.KoinTest
import org.koin.test.inject
import kotlin.getValue

class DummyOnboardingModuleTest : KoinTest, FunSpec() {
    init {
        extension(KoinExtension(modules = listOf(CoreAuthModule, DummyOnboardingModule)))

        val registerUseCase: RegisterUseCase by inject()
        val confirmEmailUseCase: ConfirmEmailUseCase by inject()
        val setPasswordUseCase: SetPasswordUseCase by inject()
        val loginUseCase: LoginUseCase by inject()
        val forgotPasswordUseCase: ForgotPasswordUseCase by inject()
        val resetPasswordUseCase: ResetPasswordUseCase by inject()

        test("all use cases are resolvable with correct types") {
            registerUseCase.shouldBeInstanceOf<DummyRegisterUseCase>()
            confirmEmailUseCase.shouldBeInstanceOf<DummyConfirmEmailUseCase>()
            setPasswordUseCase.shouldBeInstanceOf<DummySetPasswordUseCase>()
            loginUseCase.shouldBeInstanceOf<DummyLoginUseCase>()
            forgotPasswordUseCase.shouldBeInstanceOf<DummyForgotPasswordUseCase>()
            resetPasswordUseCase.shouldBeInstanceOf<DummyResetPasswordUseCase>()
        }
    }
}
