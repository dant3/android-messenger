package amber.ui.navhost

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject

@Composable
fun NavHostScaffold(modifier: Modifier = Modifier) {
    val navHostBuilderInfrastructure = koinInject<NavHostBuilderInfrastructure>()
    val navController = rememberNavController()

    AuthGuardedNavHost(
        navController = navController,
        modifier = modifier,
    ) {
        navHostBuilderInfrastructure.applyTo(this, navController)
    }
}
