package amber.arch.coroutines

import amber.arch.lifecycle.InitPriority
import amber.arch.lifecycle.LifecycleComponent
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

class CoroutineScopeProvider : LifecycleComponent {
    override val priority: InitPriority = InitPriority.MIN

    val scope: CoroutineScope = CoroutineScope(
        SupervisorJob() +
            Dispatchers.Default +
            CoroutineName("Core") +
            LoggingCoroutineExceptionHandler("Scope.Core"),
    )

    override fun dispose() {
        scope.cancel("Application is disposed")
    }

    companion object {
        fun provide(provider: CoroutineScopeProvider): CoroutineScope = provider.scope
    }
}