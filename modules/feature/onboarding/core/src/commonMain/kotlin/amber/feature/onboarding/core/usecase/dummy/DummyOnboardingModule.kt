package amber.feature.onboarding.core.usecase.dummy

import amber.feature.onboarding.core.usecase.ConfirmEmailUseCase
import amber.feature.onboarding.core.usecase.ForgotPasswordUseCase
import amber.feature.onboarding.core.usecase.LoginUseCase
import amber.feature.onboarding.core.usecase.RegisterUseCase
import amber.feature.onboarding.core.usecase.ResetPasswordUseCase
import amber.feature.onboarding.core.usecase.SetPasswordUseCase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val DummyOnboardingModule: Module = module {
    singleOf(::DummyRegisterUseCase) bind RegisterUseCase::class
    singleOf(::DummyConfirmEmailUseCase) bind ConfirmEmailUseCase::class
    singleOf(::DummySetPasswordUseCase) bind SetPasswordUseCase::class
    singleOf(::DummyLoginUseCase) bind LoginUseCase::class
    singleOf(::DummyForgotPasswordUseCase) bind ForgotPasswordUseCase::class
    singleOf(::DummyResetPasswordUseCase) bind ResetPasswordUseCase::class
}
