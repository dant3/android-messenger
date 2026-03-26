package amber.arch.lifecycle

import amber.arch.splash.SplashController
import org.koin.core.module.Module
import org.koin.dsl.module

val CoreLifecycleModule: Module = module {
    single { LifecycleManager(getAll()) }
    single { SplashController() }
}
