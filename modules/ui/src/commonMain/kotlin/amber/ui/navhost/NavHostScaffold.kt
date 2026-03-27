package amber.ui.navhost

import amber.modules.core.uikit.generated.resources.Res
import amber.modules.core.uikit.generated.resources.app_name
import amber.navigation.routes.CounterRoute
import amber.navigation.routes.OnboardingRoute
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHostScaffold(modifier: Modifier = Modifier) {
    val navHostBuilderInfrastructure = koinInject<NavHostBuilderInfrastructure>()

    Scaffold(
        topBar = {
            Surface(shadowElevation = 3.dp) {
                CenterAlignedTopAppBar(title = {
                    Text(
                        text = stringResource(resource = Res.string.app_name),
                    )
                })
            }
        },
        modifier = modifier,
    ) { padding ->
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = OnboardingRoute,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            navHostBuilderInfrastructure.applyTo(this, navController)
        }
    }
}