package amber.logger

import amber.arch.lifecycle.lifecycleComponent
import dev.shivathapaa.logger.sink.DefaultLogSink
import dev.shivathapaa.logger.sink.LogSink
import org.koin.core.module.Module
import org.koin.dsl.bind
import org.koin.dsl.module

val CoreLoggerModule: Module = module {
    single { DefaultLogSink() } bind LogSink::class
    lifecycleComponent { LoggerConfigurator(sinks = getAll()) }
}
