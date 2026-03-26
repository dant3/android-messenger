package amber.messenger.android.activity

import amber.arch.splash.SplashController
import amber.ui.AppUi
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.android.ext.android.inject

class AmberUiActivity : AppCompatActivity() {
    private val splashController: SplashController by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen().setKeepOnScreenCondition {
            !splashController.isReady.value
        }
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppUi(modifier = Modifier.fillMaxSize())
        }
    }
}
