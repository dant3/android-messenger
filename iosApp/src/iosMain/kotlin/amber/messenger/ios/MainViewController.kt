package amber.messenger.ios

import amber.arch.splash.SplashController
import amber.ui.AppModules
import amber.ui.AppUi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.getKoin

fun MainViewController() = ComposeUIViewController {
    AppUi(modifier = Modifier.fillMaxSize())
}

fun initKoin() {
    startKoin { modules(AppModules) }
    getKoin().get<SplashController>().markReady()
}
