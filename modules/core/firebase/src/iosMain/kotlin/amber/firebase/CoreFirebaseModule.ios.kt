package amber.firebase

import amber.analytics.AnalyticsTracker
import amber.arch.lifecycle.lifecycleComponent
import dev.shivathapaa.logger.sink.LogSink
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

actual val CoreFirebaseModule: Module = module {
    lifecycleComponent { IosFirebaseInitializer() }
    single { IosCrashlyticsLogSink() } bind LogSink::class
    single { IosFirebaseAnalyticsTracker() } bind AnalyticsTracker::class
}
