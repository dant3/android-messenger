package amber.ui

import amber.navigation.routes.OnboardingRoute
import amber.ui.navhost.NavHostScaffold
import amber.ui.splash.SplashOverlayController
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import org.koin.compose.getKoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUi(modifier: Modifier = Modifier) {
    AppTheme {
        SplashOverlayController(modifier = modifier) {
            NavHostScaffold(
                startDestination = OnboardingRoute,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
