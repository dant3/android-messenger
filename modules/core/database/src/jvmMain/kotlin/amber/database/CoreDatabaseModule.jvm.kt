package amber.database

import org.koin.core.module.Module
import org.koin.dsl.module

actual val CoreDatabaseModule: Module = module {
    single { SqlDriverFactory() }
}
