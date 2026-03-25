package amber.arch.lifecycle

internal class RecordingComponent(
    private val name: String,
    override val priority: InitPriority,
    private val callLog: MutableList<String>,
) : LifecycleComponent {
    override fun init() {
        callLog.add("$name:init")
    }

    override fun dispose() {
        callLog.add("$name:dispose")
    }
}