package amber.ui.navhost

import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.app_name
import amber.modules.core.uikit.generated.resources.logout_dialog_title
import amber.navigation.routes.LogoutDialogRoute
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHostScaffold(modifier: Modifier = Modifier) {
    val navHostBuilderInfrastructure = koinInject<NavHostBuilderInfrastructure>()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(resource = Res.string.app_name),
                        )
                    },
                    actions = {
                        // TODO: temporary logout button for testing
                        IconButton(onClick = { navController.navigate(LogoutDialogRoute) }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = stringResource(Res.string.logout_dialog_title),
                            )
                        }
                    },
                )
            }
        },
        modifier = modifier,
    ) { padding ->
        AuthGuardedNavHost(
            navController = navController,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            navHostBuilderInfrastructure.applyTo(this, navController)
        }
    }
}