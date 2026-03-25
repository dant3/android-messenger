package amber.arch.lifecycle

internal class FailingComponent(
    override val priority: InitPriority,
    private val failOnInit: Throwable? = null,
    private val failOnDispose: Throwable? = null,
) : LifecycleComponent {
    override suspend fun init() {
        failOnInit?.let { throw it }
    }

    override suspend fun dispose() {
        failOnDispose?.let { throw it }
    }
}