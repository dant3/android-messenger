package amber.feature.counter.ui

import amber.navigation.navGraphComponent
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val CounterFeatureUiModule: Module = module {
    navGraphComponent(singleOf(::CounterNavGraphComponent))
}
