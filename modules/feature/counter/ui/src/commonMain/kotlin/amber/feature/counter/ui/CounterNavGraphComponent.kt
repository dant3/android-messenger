package amber.feature.counter.ui

import amber.feature.counter.core.CounterStore
import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.app_name
import amber.modules.core.uikit.generated.resources.logout_dialog_title
import amber.navigation.NavGraphComponent
import amber.navigation.routes.CounterRoute
import amber.navigation.routes.LogoutDialogRoute
import amber.ui.uikit.scaffold.NavigableTopAppBar
import amber.ui.uikit.scaffold.ScreenScaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

class CounterNavGraphComponent : NavGraphComponent {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun NavGraphBuilder.configureDestinations(navController: NavController) {
        composable<CounterRoute> {
            val counterStore: CounterStore = koinInject()
            ScreenScaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = {
                    NavigableTopAppBar(
                        title = stringResource(Res.string.app_name),
                        actions = {
                            IconButton(onClick = { navController.navigate(LogoutDialogRoute) }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                    contentDescription = stringResource(Res.string.logout_dialog_title),
                                )
                            }
                        },
                    )
                },
            ) { padding ->
                CounterScreen(
                    store = counterStore,
                    modifier = Modifier.padding(padding),
                )
            }
        }
    }
}
