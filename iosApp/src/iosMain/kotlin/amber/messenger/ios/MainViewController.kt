package amber.messenger.ios

import amber.ui.uikit.TestScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController

fun MainViewController() = ComposeUIViewController {
    TestScreen(modifier = Modifier.fillMaxSize())
}
