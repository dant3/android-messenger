package amber.power

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val CorePowerMonitorModule: Module = module {
    singleOf(::AndroidPowerMonitor).bind<PowerMonitor>()
}
