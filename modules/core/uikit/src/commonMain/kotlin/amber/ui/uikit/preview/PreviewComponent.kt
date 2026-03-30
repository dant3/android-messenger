package amber.ui.uikit.preview

import amber.ui.uikit.theme.AmberMaterialTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Provides everything necessary to correctly show previews. Use together with @Preview/@UiPreview.
 */
@Composable
@Suppress("ComposeParameterOrder")
fun PreviewComponent(
    modifier: Modifier = Modifier,
    darkTheme: Boolean = isSystemInDarkTheme(),
    component: @Composable () -> Unit,
) {
    AmberMaterialTheme(darkTheme = darkTheme) {
        Surface(modifier = modifier) {
            component()
        }
    }
}
