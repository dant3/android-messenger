package amber.ui.logout

import amber.auth.AuthTokenStorage
import amber.navigation.NavGraphComponent
import amber.navigation.routes.LogoutDialogRoute
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.dialog
import org.koin.compose.koinInject

class LogoutNavGraphComponent : NavGraphComponent {
    override fun NavGraphBuilder.configureDestinations(navController: NavController) {
        dialog<LogoutDialogRoute> {
            val authTokenStorage = koinInject<AuthTokenStorage>()
            LogoutDialog(
                onConfirm = { authTokenStorage.clearAll() },
                onDismiss = { navController.popBackStack() },
            )
        }
    }
}
