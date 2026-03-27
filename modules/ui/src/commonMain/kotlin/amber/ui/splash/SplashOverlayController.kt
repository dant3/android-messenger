package amber.ui.splash

import amber.arch.splash.SplashController
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@Composable
fun SplashOverlayController(modifier: Modifier = Modifier, appContent: @Composable () -> Unit) {
    val splashController: SplashController = koinInject()
    val isReady by splashController.isReady.collectAsState()

    AnimatedContent(
        targetState = isReady,
        transitionSpec = { fadeIn() togetherWith fadeOut() },
        modifier = modifier,
    ) { ready ->
        if (ready) {
            appContent()
        } else {
            SplashOverlay(modifier = Modifier.fillMaxSize())
        }
    }
}