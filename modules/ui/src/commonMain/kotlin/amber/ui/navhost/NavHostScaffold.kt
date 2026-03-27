package amber.ui.navhost

import amber.navigation.routes.CounterRoute
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavHostScaffold(modifier: Modifier = Modifier) {
    val navHostBuilderInfrastructure = koinInject<NavHostBuilderInfrastructure>()

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
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = CounterRoute,
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
        ) {
            navHostBuilderInfrastructure.applyTo(this, navController)
        }
    }
}