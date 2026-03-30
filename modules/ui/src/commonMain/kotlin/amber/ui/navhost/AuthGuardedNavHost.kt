package amber.ui.navhost

import amber.auth.AuthStateProvider
import amber.navigation.routes.CounterRoute
import amber.navigation.routes.OnboardingRoute
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import kotlinx.coroutines.flow.drop
import org.koin.compose.koinInject

@Composable
fun AuthGuardedNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    builder: NavGraphBuilder.() -> Unit,
) {
    val authStateProvider: AuthStateProvider = koinInject()
    val startDestination = if (authStateProvider.isAuthenticated.value) CounterRoute else OnboardingRoute

    LaunchedEffect(Unit) {
        authStateProvider.isAuthenticated
            .drop(1)
            .collect { authenticated ->
                if (!authenticated) {
                    navController.navigate(OnboardingRoute) {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        builder = builder,
    )
}
