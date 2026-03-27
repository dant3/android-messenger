package amber.messenger.android

import amber.arch.splash.SplashController
import amber.runtime.AppModules
import android.app.Application
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class AmberApplicationInstance : Application() {
    private val splashController: SplashController by inject()

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AmberApplicationInstance)
            modules(AppModules)
        }
        splashController.markReady()
    }
}
