package amber.firebase

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dev.shivathapaa.logger.api.LogLevel
import dev.shivathapaa.logger.core.LogEvent
import dev.shivathapaa.logger.sink.LogSink

class CrashlyticsLogSink : LogSink {
    override fun emit(event: LogEvent) {
        if (event.level < LogLevel.WARN) return

        val crashlytics = FirebaseCrashlytics.getInstance()
        val message = "[${event.loggerName}] ${event.message.orEmpty()}"
        crashlytics.log(message)

        if (event.level >= LogLevel.ERROR) {
            val throwable = event.throwable ?: RuntimeException(message)
            crashlytics.recordException(throwable)
        }
    }
}
