package amber.ui

import amber.ui.navhost.NavHostScaffold
import amber.ui.splash.SplashOverlayController
import amber.ui.uikit.theme.AmberMaterialTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUi(modifier: Modifier = Modifier) {
    AmberMaterialTheme {
        SplashOverlayController(modifier = modifier) {
            NavHostScaffold(modifier = Modifier.fillMaxSize())
        }
    }
}