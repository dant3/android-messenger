package amber.arch.coroutines

import dev.shivathapaa.logger.api.LoggerFactory
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler

class LoggingCoroutineExceptionHandler(name: String) :
    AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
    private val log = LoggerFactory.get(name)

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        if (exception !is CancellationException) {
            log.error(exception) { "Coroutine failed" }
        }
    }
}
