package amber.runtime

import amber.arch.CoreArchModule
import amber.auth.CoreAuthModule
import amber.client.impl.CoreClientModule
import amber.database.AppDatabaseModule
import amber.database.SqlDriverFactoryModule
import amber.feature.counter.core.CounterFeatureModule
import amber.feature.counter.ui.CounterFeatureUiModule
import amber.feature.onboarding.core.usecase.dummy.DummyOnboardingModule
import amber.feature.onboarding.ui.OnboardingFeatureUiModule
import amber.firebase.CoreFirebaseModule
import amber.logger.CoreLoggerModule
import amber.mediaplayer.CoreMediaPlayerModule
import amber.network.CoreNetworkMonitorModule
import amber.power.CorePowerMonitorModule
import amber.preferences.CorePreferencesModule
import amber.ui.navhost.NavHostModule
import amber.webrtc.CoreWebRtcModule
import org.koin.core.module.Module

val AppModules: List<Module> = listOf(
    CoreArchModule,
    CoreLoggerModule,
    CoreFirebaseModule,
    CoreAuthModule,
    CoreClientModule,
    SqlDriverFactoryModule,
    CoreMediaPlayerModule,
    CoreNetworkMonitorModule,
    CorePowerMonitorModule,
    CorePreferencesModule,
    CoreWebRtcModule,
    AppDatabaseModule,
    CounterFeatureModule,
    CounterFeatureUiModule,
    DummyOnboardingModule,
    OnboardingFeatureUiModule,
    NavHostModule,
)
