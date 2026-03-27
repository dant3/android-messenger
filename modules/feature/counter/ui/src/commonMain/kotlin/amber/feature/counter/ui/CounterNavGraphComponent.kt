package amber.feature.counter.ui

import amber.feature.counter.core.CounterStore
import amber.navigation.NavGraphComponent
import amber.navigation.routes.CounterRoute
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.koinInject

class CounterNavGraphComponent : NavGraphComponent {
    override fun NavGraphBuilder.configureDestinations(navController: NavController) {
        composable<CounterRoute> {
            val counterStore: CounterStore = koinInject()
            CounterScreen(
                store = counterStore,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }

}