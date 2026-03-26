package amber.ui

import amber.arch.lifecycle.CoreLifecycleModule
import amber.database.CoreDatabaseModule
import amber.feature.counter.core.CounterFeatureModule
import org.koin.core.module.Module

val AppModules: List<Module> = listOf(
    CoreLifecycleModule,
    CoreDatabaseModule,
    CounterFeatureModule,
)
