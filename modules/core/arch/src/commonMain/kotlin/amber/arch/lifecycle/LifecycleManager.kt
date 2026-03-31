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
        val failures = withContext(Dispatchers.Default) {
            runAll(components.sortedByDescending { it.priority }) {
                log.verbose { "Running $it init" }
                it.init()
            }
        }
        throwCollected(failures)
        splashController.markReady()
    }

    suspend fun dispose() {
        val failures = withContext(Dispatchers.Default) {
            runAll(components.sortedBy { it.priority }) {
                log.verbose { "Running $it dispose" }
                it.dispose()
            }
        }
        throwCollected(failures)
    }

    private inline fun runAll(
        sortedComponentsList: List<LifecycleComponent>,
        action: (LifecycleComponent) -> Unit,
    ): List<Exception> {
        val failures = mutableListOf<Exception>()
        for (component in sortedComponentsList) {
            try {
                action(component)
            } catch (@Suppress("TooGenericExceptionCaught") e: Exception) {
                failures.add(e)
            }
        }
        return failures
    }

    private fun throwCollected(failures: List<Exception>) {
        if (failures.isEmpty()) return
        val first = failures.first()
        for (i in 1 until failures.size) {
            first.addSuppressed(failures[i])
        }
        throw first
    }
}
