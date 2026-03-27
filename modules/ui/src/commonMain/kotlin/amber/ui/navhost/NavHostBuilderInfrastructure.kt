package amber.ui.navhost

import amber.navigation.NavGraphComponent
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

class NavHostBuilderInfrastructure(private val components: List<NavGraphComponent>) {
    fun applyTo(builder: NavGraphBuilder, navController: NavController): Unit = with(builder) {
        components.forEach { with(it) { configureDestinations(navController) } }
    }
}