package amber.arch.lifecycle

internal class FailingComponent(
    override val priority: InitPriority,
    private val failOnInit: Throwable? = null,
    private val failOnDispose: Throwable? = null,
) : LifecycleComponent {
    override fun init() {
        failOnInit?.let { throw it }
    }

    override fun dispose() {
        failOnDispose?.let { throw it }
    }
}