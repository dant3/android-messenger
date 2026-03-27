package amber.ui

import amber.preferences.AppPreferences
import amber.ui.uikit.theme.AmberMaterialTheme
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import org.koin.compose.koinInject

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val preferences = koinInject<AppPreferences>()
    val theme by preferences.theme.observe().collectAsState(preferences.theme.defaultValue)

    val isDarkTheme = when (theme) {
        AppPreferences.Theme.LIGHT -> false
        AppPreferences.Theme.DARK -> true
        AppPreferences.Theme.SYSTEM -> isSystemInDarkTheme()
    }

    AmberMaterialTheme(darkTheme = isDarkTheme, content = content)
}