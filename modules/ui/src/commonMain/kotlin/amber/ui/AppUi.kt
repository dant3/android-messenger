package amber.ui

import amber.arch.splash.SplashController
import amber.feature.counter.core.CounterStore
import amber.feature.counter.ui.CounterScreen
import amber.ui.uikit.theme.AmberMaterialTheme
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppUi(modifier: Modifier = Modifier) {
    AmberMaterialTheme {
        val splashController: SplashController = koinInject()
        val isReady by splashController.isReady.collectAsState()

        AnimatedContent(
            targetState = isReady,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            modifier = modifier,
        ) { ready ->
            if (ready) {
                AppContent(modifier = Modifier.fillMaxSize())
            } else {
                SplashOverlay(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
private fun SplashOverlay(modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier,
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = "Amber Messenger",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppContent(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = "Amber Messenger",
                )
            })
        },
        modifier = modifier,
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            val counterStore: CounterStore = koinInject()
            CounterScreen(
                store = counterStore,
                modifier = Modifier.align(Alignment.Center),
            )
        }
    }
}
