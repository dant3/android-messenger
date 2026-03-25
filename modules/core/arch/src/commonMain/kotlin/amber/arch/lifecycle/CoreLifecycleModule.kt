package amber.arch.lifecycle

import org.koin.core.module.Module
import org.koin.dsl.module

val CoreLifecycleModule: Module = module {
    single { LifecycleManager(getAll()) }
}
