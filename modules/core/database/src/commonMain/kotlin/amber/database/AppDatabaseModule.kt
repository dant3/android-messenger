package amber.database

import org.koin.core.module.Module
import org.koin.dsl.module

val AppDatabaseModule: Module = module {
    single {
        val factory: SqlDriverFactory = get()
        AppDatabase(factory.create(AppDatabase.Schema, "amber-messenger.db"))
    }
    single { get<AppDatabase>().counterQueries }
}
