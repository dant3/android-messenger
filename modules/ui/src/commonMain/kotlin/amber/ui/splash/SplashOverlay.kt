package amber.ui.splash

import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.app_name
import amber.ui.uikit.preview.PreviewComponent
import amber.ui.uikit.preview.UiPreview
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource

@Composable
fun SplashOverlay(modifier: Modifier = Modifier.Companion) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(Res.string.app_name),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@UiPreview
@Composable
private fun SplashOverlayPreview() = PreviewComponent {
    SplashOverlay()
}