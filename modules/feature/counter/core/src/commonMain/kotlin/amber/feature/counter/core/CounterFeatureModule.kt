package amber.feature.counter.core

import org.koin.core.module.Module
import org.koin.dsl.module

val CounterFeatureModule: Module = module {
    single { CounterStore(get()) }
}
