package amber.arch.lifecycle

import amber.arch.splash.SplashController
import amber.core.utils.logging.logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LifecycleManager(
    private val components: List<LifecycleComponent>,
    private val splashController: SplashController,
) {
    private val log = logger {}

    suspend fun init() {
        withContext(Dispatchers.Default) {
            runAll(components.sortedByDescending { it.priority }) {
                log.verbose { "Running $it init" }
                it.init()
            }
        }
        splashController.markReady()
    }

    suspend fun dispose() {
        withContext(Dispatchers.Default) {
            runAll(components.sortedBy { it.priority }) {
                log.verbose { "Running $it dispose" }
                it.dispose()
            }
        }
    }

    private suspend inline fun runAll(
        sortedComponentsList: List<LifecycleComponent>,
        action: (LifecycleComponent) -> Unit,
    ) {
        var firstFailure: Exception? = null
        for (component in sortedComponentsList) {
            try {
                action(component)
            } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                if (firstFailure == null) {
                    firstFailure = e
                } else {
                    firstFailure.addSuppressed(e)
                }
            }
        }
        firstFailure?.let { throw it }
    }
}
