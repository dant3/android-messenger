package amber.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder

interface NavGraphComponent {
    fun NavGraphBuilder.configureDestinations(navController: NavController)
}
