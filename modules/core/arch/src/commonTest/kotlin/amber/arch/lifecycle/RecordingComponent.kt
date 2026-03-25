package amber.arch.lifecycle

internal class RecordingComponent(
    private val name: String,
    override val priority: InitPriority,
    private val callLog: MutableList<String>,
) : LifecycleComponent {
    override suspend fun init() {
        callLog.add("$name:init")
    }

    override suspend fun dispose() {
        callLog.add("$name:dispose")
    }
}