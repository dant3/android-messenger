package amber.ui.splash

import amber.arch.splash.SplashController
import amber.ui.uikit.transition.LocalAnimatedVisibilityScope
import amber.ui.uikit.transition.LocalSharedTransitionScope
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SplashOverlayController(modifier: Modifier = Modifier, appContent: @Composable () -> Unit) {
    val splashController: SplashController = koinInject()
    val isReady by splashController.isReady.collectAsState()

    SharedTransitionLayout(modifier = modifier) {
        AnimatedContent(
            targetState = isReady,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
        ) { ready ->
            CompositionLocalProvider(
                LocalSharedTransitionScope provides this@SharedTransitionLayout,
                LocalAnimatedVisibilityScope provides this@AnimatedContent,
            ) {
                if (ready) {
                    appContent()
                } else {
                    SplashOverlay(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}
