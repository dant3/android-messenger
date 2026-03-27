package amber.feature.onboarding.ui

import amber.feature.onboarding.core.OnboardingStore
import amber.navigation.NavGraphComponent
import amber.navigation.routes.OnboardingRoute
import amber.navigation.routes.CounterRoute
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.compose.koinInject

class OnboardingNavGraphComponent : NavGraphComponent {
    override fun NavGraphBuilder.configureDestinations(navController: NavController) {
        composable<OnboardingRoute> {
            val onboardingStore: OnboardingStore = koinInject()
            OnboardingScreen(
                store = onboardingStore,
                onComplete = {
                    navController.navigate(CounterRoute) {
                        popUpTo<OnboardingRoute> { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
