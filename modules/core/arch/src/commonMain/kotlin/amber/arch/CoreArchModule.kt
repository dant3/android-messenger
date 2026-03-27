package amber.arch

import amber.arch.coroutines.CoroutineScopeProvider
import amber.arch.lifecycle.LifecycleManager
import amber.arch.lifecycle.lifecycleComponent
import amber.arch.splash.SplashController
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val CoreArchModule: Module = module {
    single {
        LifecycleManager(
            components = getAll(),
            splashController = get(),
        )
    }
    single {
        SplashController()
    }
    lifecycleComponent {
        CoroutineScopeProvider()
    }
    factoryOf(CoroutineScopeProvider::provide)
}
