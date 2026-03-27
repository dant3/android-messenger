package amber.ui

import amber.arch.lifecycle.CoreLifecycleModule
import amber.database.AppDatabaseModule
import amber.database.CoreDatabaseModule
import amber.feature.counter.core.CounterFeatureModule
import amber.feature.counter.ui.CounterFeatureUiModule
import amber.feature.onboarding.core.OnboardingFeatureModule
import amber.feature.onboarding.ui.OnboardingFeatureUiModule
import amber.ui.navhost.NavHostModule
import org.koin.core.module.Module

val AppModules: List<Module> = listOf(
    CoreLifecycleModule,
    CoreDatabaseModule,
    AppDatabaseModule,
    CounterFeatureModule,
    CounterFeatureUiModule,
    OnboardingFeatureModule,
    OnboardingFeatureUiModule,
    NavHostModule,
)
