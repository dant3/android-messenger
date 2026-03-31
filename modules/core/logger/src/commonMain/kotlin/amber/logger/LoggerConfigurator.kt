package amber.logger

import amber.arch.lifecycle.InitPriority
import amber.arch.lifecycle.LifecycleComponent
import dev.shivathapaa.logger.api.LogLevel
import dev.shivathapaa.logger.api.LoggerFactory
import dev.shivathapaa.logger.core.LoggerConfig
import dev.shivathapaa.logger.sink.LogSink

class LoggerConfigurator(
    private val sinks: List<LogSink>,
) : LifecycleComponent {
    override val priority: InitPriority = InitPriority.MAX

    override fun init() {
        val builder = LoggerConfig.Builder().minLevel(LogLevel.DEBUG)
        sinks.forEach { builder.addSink(it) }
        LoggerFactory.install(builder.build())
    }
}
