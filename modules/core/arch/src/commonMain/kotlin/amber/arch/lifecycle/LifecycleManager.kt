package amber.arch.lifecycle

import amber.core.utils.logging.logger

class LifecycleManager(
    private val components: List<LifecycleComponent>,
) {
    private val log = logger {}

    fun init() {
        runAll(components.sortedByDescending { it.priority }) {
            log.verbose { "Running $it init" }
            it.init()
        }
    }

    fun dispose() {
        runAll(components.sortedBy { it.priority }) {
            log.verbose { "Running $it dispose" }
            it.dispose()
        }
    }

    private inline fun runAll(
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
