package amber.firebase

import amber.analytics.AnalyticsTracker
import amber.analytics.NoOpAnalyticsTracker
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val CoreFirebaseModule: Module = module {
    single { NoOpAnalyticsTracker() } bind AnalyticsTracker::class
}
