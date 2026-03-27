package amber.arch.lifecycle

interface LifecycleComponent {
    val priority: InitPriority get() = InitPriority.DEFAULT

    fun init() {}
    fun dispose() {}
}
