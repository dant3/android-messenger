package amber.database

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val SqlDriverFactoryModule: Module = module {
    singleOf(::SqlDriverFactory)
}
