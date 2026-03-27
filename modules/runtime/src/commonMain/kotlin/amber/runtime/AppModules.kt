package amber.runtime

import amber.arch.CoreArchModule
import amber.database.AppDatabaseModule
import amber.database.CoreDatabaseModule
import amber.mediaplayer.CoreMediaPlayerModule
import amber.feature.counter.core.CounterFeatureModule
import amber.feature.counter.ui.CounterFeatureUiModule
import amber.feature.onboarding.core.OnboardingFeatureModule
import amber.feature.onboarding.ui.OnboardingFeatureUiModule
import amber.preferences.CorePreferencesModule
import amber.ui.navhost.NavHostModule
import org.koin.core.module.Module

val AppModules: List<Module> = listOf(
    CoreArchModule,
    CoreDatabaseModule,
    CoreMediaPlayerModule,
    CorePreferencesModule,
    AppDatabaseModule,
    CounterFeatureModule,
    CounterFeatureUiModule,
    OnboardingFeatureModule,
    OnboardingFeatureUiModule,
    NavHostModule,
)
