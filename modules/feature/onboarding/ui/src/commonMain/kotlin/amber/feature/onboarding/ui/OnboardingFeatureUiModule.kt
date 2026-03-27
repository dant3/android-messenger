package amber.feature.onboarding.ui

import amber.navigation.navGraphComponent
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val OnboardingFeatureUiModule: Module = module {
    navGraphComponent(singleOf(::OnboardingNavGraphComponent))
}
