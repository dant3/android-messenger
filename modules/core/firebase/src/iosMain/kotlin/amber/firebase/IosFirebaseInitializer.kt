package amber.firebase

import amber.arch.lifecycle.InitPriority
import amber.arch.lifecycle.LifecycleComponent
import cocoapods.FirebaseCore.FIRApp
import kotlinx.cinterop.ExperimentalForeignApi

@OptIn(ExperimentalForeignApi::class)
class IosFirebaseInitializer : LifecycleComponent {
    override val priority: InitPriority = InitPriority.MAX - 1

    override fun init() {
        FIRApp.configure()
    }
}
