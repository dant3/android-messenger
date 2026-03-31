package amber.firebase

import amber.arch.lifecycle.InitPriority
import amber.arch.lifecycle.LifecycleComponent
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.crashlytics.FirebaseCrashlytics

class FirebaseInitializer(
    private val context: Context,
) : LifecycleComponent {
    override val priority: InitPriority = InitPriority.MAX - 1

    override fun init() {
        FirebaseApp.initializeApp(context)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)
    }
}
