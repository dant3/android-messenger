package amber.arch.lifecycle

interface LifecycleComponent {
    val priority: InitPriority get() = InitPriority.DEFAULT

    suspend fun init()
    suspend fun dispose() {}
}
