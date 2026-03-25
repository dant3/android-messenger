package amber.arch.lifecycle

class LifecycleManager(
    private val components: List<LifecycleComponent>,
) {
    suspend fun init() {
        runAll(components.sortedByDescending { it.priority }) { it.init() }
    }

    suspend fun dispose() {
        runAll(components.sortedBy { it.priority }) { it.dispose() }
    }

    private suspend inline fun runAll(
        sorted: List<LifecycleComponent>,
        action: (LifecycleComponent) -> Unit,
    ) {
        var firstFailure: Exception? = null
        for (component in sorted) {
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
