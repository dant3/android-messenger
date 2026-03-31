package amber.feature.onboarding.ui

import amber.feature.onboarding.ui.routes.EmailConfirmationRoute
import amber.feature.onboarding.ui.routes.ForgotPasswordRoute
import amber.feature.onboarding.ui.routes.LoginRoute
import amber.feature.onboarding.ui.routes.RegisterRoute
import amber.feature.onboarding.ui.routes.ResetPasswordRoute
import amber.feature.onboarding.ui.routes.SetPasswordRoute
import amber.feature.onboarding.ui.routes.WelcomeRoute
import amber.feature.onboarding.ui.screen.EmailConfirmationScreen
import amber.feature.onboarding.ui.screen.ForgotPasswordScreen
import amber.feature.onboarding.ui.screen.LoginScreen
import amber.feature.onboarding.ui.screen.RegisterScreen
import amber.feature.onboarding.ui.screen.ResetPasswordScreen
import amber.feature.onboarding.ui.screen.SetPasswordScreen
import amber.feature.onboarding.ui.screen.WelcomeScreen
import amber.navigation.NavGraphComponent
import amber.navigation.routes.CounterRoute
import amber.navigation.routes.OnboardingRoute
import amber.ui.uikit.scaffold.BackTopBar
import amber.ui.uikit.scaffold.ScreenScaffold
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.koin.compose.viewmodel.koinViewModel

private const val TRANSITION_DURATION_MS = 350

private val enterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(TRANSITION_DURATION_MS))
}

private val exitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(TRANSITION_DURATION_MS))
}

private val popEnterTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(TRANSITION_DURATION_MS))
}

private val popExitTransition: AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(TRANSITION_DURATION_MS))
}

@Composable
private fun rememberOnboardingViewModel(
    navController: NavController,
    backStackEntry: NavBackStackEntry,
): OnboardingViewModel {
    val parentEntry = remember(backStackEntry) {
        navController.getBackStackEntry<OnboardingRoute>()
    }
    return koinViewModel(viewModelStoreOwner = parentEntry)
}

class OnboardingNavGraphComponent : NavGraphComponent {
    override fun NavGraphBuilder.configureDestinations(navController: NavController) {
        navigation(
            startDestination = WelcomeRoute,
            route = OnboardingRoute::class,
        ) {
            composable<WelcomeRoute>(
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition,
            ) {
                WelcomeScreen(
                    onRegister = { navController.navigate(RegisterRoute) },
                    onLogin = { navController.navigate(LoginRoute) },
                    modifier = Modifier.fillMaxSize(),
                )
            }

            composable<RegisterRoute>(
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition,
            ) { backStackEntry ->
                val viewModel = rememberOnboardingViewModel(navController, backStackEntry)
                val state by viewModel.state.collectAsState()
                ScreenScaffold(
                    topBar = { BackTopBar(onNavigateUp = { navController.navigateUp() }) },
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    RegisterScreen(
                        state = state,
                        onEmailChange = viewModel::updateEmail,
                        onRegister = {
                            viewModel.register {
                                navController.navigate(EmailConfirmationRoute(state.email))
                            }
                        },
                        modifier = Modifier.fillMaxSize().padding(padding),
                    )
                }
            }

            composable<EmailConfirmationRoute>(
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition,
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<EmailConfirmationRoute>()
                val viewModel = rememberOnboardingViewModel(navController, backStackEntry)
                val state by viewModel.state.collectAsState()
                ScreenScaffold(
                    topBar = { BackTopBar(onNavigateUp = { navController.navigateUp() }) },
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    EmailConfirmationScreen(
                        email = route.email,
                        state = state,
                        onCodeChange = viewModel::updateConfirmationCode,
                        onConfirm = {
                            viewModel.confirmEmail(route.email) {
                                navController.navigate(SetPasswordRoute(route.email))
                            }
                        },
                        modifier = Modifier.fillMaxSize().padding(padding),
                    )
                }
            }

            composable<SetPasswordRoute>(
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition,
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<SetPasswordRoute>()
                val viewModel = rememberOnboardingViewModel(navController, backStackEntry)
                val state by viewModel.state.collectAsState()
                ScreenScaffold(
                    topBar = { BackTopBar(onNavigateUp = { navController.navigateUp() }) },
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    SetPasswordScreen(
                        state = state,
                        onPasswordChange = viewModel::updatePassword,
                        onSetPassword = {
                            viewModel.setPassword(route.email) {
                                navController.navigate(CounterRoute) {
                                    popUpTo<OnboardingRoute> { inclusive = true }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize().padding(padding),
                    )
                }
            }

            composable<LoginRoute>(
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition,
            ) { backStackEntry ->
                val viewModel = rememberOnboardingViewModel(navController, backStackEntry)
                val state by viewModel.state.collectAsState()
                ScreenScaffold(
                    topBar = { BackTopBar(onNavigateUp = { navController.navigateUp() }) },
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    LoginScreen(
                        state = state,
                        onEmailChange = viewModel::updateEmail,
                        onPasswordChange = viewModel::updatePassword,
                        onLogin = {
                            viewModel.login {
                                navController.navigate(CounterRoute) {
                                    popUpTo<OnboardingRoute> { inclusive = true }
                                }
                            }
                        },
                        onForgotPassword = { navController.navigate(ForgotPasswordRoute) },
                        modifier = Modifier.fillMaxSize().padding(padding),
                    )
                }
            }

            composable<ForgotPasswordRoute>(
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition,
            ) { backStackEntry ->
                val viewModel = rememberOnboardingViewModel(navController, backStackEntry)
                val state by viewModel.state.collectAsState()
                ScreenScaffold(
                    topBar = { BackTopBar(onNavigateUp = { navController.navigateUp() }) },
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    ForgotPasswordScreen(
                        state = state,
                        onEmailChange = viewModel::updateEmail,
                        onSendResetCode = {
                            viewModel.forgotPassword {
                                navController.navigate(ResetPasswordRoute(state.email))
                            }
                        },
                        modifier = Modifier.fillMaxSize().padding(padding),
                    )
                }
            }

            composable<ResetPasswordRoute>(
                enterTransition = enterTransition,
                exitTransition = exitTransition,
                popEnterTransition = popEnterTransition,
                popExitTransition = popExitTransition,
            ) { backStackEntry ->
                val route = backStackEntry.toRoute<ResetPasswordRoute>()
                val viewModel = rememberOnboardingViewModel(navController, backStackEntry)
                val state by viewModel.state.collectAsState()
                ScreenScaffold(
                    topBar = { BackTopBar(onNavigateUp = { navController.navigateUp() }) },
                    modifier = Modifier.fillMaxSize(),
                ) { padding ->
                    ResetPasswordScreen(
                        email = route.email,
                        state = state,
                        onCodeChange = viewModel::updateConfirmationCode,
                        onNewPasswordChange = viewModel::updateNewPassword,
                        onResetPassword = {
                            viewModel.resetPassword(route.email) {
                                navController.navigate(CounterRoute) {
                                    popUpTo<OnboardingRoute> { inclusive = true }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxSize().padding(padding),
                    )
                }
            }
        }
    }
}
