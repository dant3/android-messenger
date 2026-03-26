package amber.feature.counter.core

import amber.database.SqlDriverFactory
import amber.feature.counter.core.db.CounterDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

val CounterFeatureModule: Module = module {
    single {
        val factory: SqlDriverFactory = get()
        CounterDatabase(factory.create(CounterDatabase.Schema, "counter.db"))
    }
    single { get<CounterDatabase>().counterQueries }
    single { CounterStore(get()) }
}
