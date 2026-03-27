package amber.feature.onboarding.core

import org.koin.core.module.Module
import org.koin.dsl.module

val OnboardingFeatureModule: Module = module {
    single { OnboardingStore() }
}
