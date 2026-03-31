package amber.network

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val CoreNetworkMonitorModule: Module = module {
    singleOf(::AndroidNetworkMonitor)
        .bind<NetworkMonitor>()
}
