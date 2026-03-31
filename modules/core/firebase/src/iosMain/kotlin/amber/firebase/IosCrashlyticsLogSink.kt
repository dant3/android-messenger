package amber.firebase

import cocoapods.FirebaseCrashlytics.FIRCrashlytics
import dev.shivathapaa.logger.api.LogLevel
import dev.shivathapaa.logger.core.LogEvent
import dev.shivathapaa.logger.sink.LogSink
import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSError

@OptIn(ExperimentalForeignApi::class)
class IosCrashlyticsLogSink : LogSink {
    override fun emit(event: LogEvent) {
        if (event.level < LogLevel.WARN) return

        val crashlytics = FIRCrashlytics.crashlytics()
        val message = "[${event.loggerName}] ${event.message.orEmpty()}"
        crashlytics.log(message)

        if (event.level >= LogLevel.ERROR) {
            val exception = event.throwable ?: return

            val error = NSError(
                domain = event.loggerName,
                code = 0,
                userInfo = mapOf(
                    "log.message" to message,
                    "exception.message" to exception.message,
                    "exception.stacktrace" to event.throwable?.stackTraceToString()
                ),
            )
            crashlytics.recordError(error)
        }
    }
}
