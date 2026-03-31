package amber.ui.splash

import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.app_logo
import amber.modules.core.uikit.generated.resources.app_name
import amber.ui.uikit.preview.PreviewComponent
import amber.ui.uikit.preview.UiPreview
import amber.ui.uikit.transition.optionalSharedElement
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashOverlay(modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(Res.drawable.app_logo),
                contentDescription = null,
                modifier = Modifier
                    .size(96.dp)
                    .optionalSharedElement("app_logo"),
            )
        }
    }
}

@UiPreview
@Composable
private fun SplashOverlayPreview() = PreviewComponent {
    SplashOverlay()
}
