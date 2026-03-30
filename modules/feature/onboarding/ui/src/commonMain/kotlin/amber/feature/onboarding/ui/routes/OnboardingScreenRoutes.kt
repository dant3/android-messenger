package amber.feature.onboarding.ui.routes

import kotlinx.serialization.Serializable

@Serializable
internal data object WelcomeRoute

@Serializable
internal data object RegisterRoute

@Serializable
internal data class EmailConfirmationRoute(val email: String)

@Serializable
internal data class SetPasswordRoute(val email: String)

@Serializable
internal data object LoginRoute

@Serializable
internal data object ForgotPasswordRoute

@Serializable
internal data class ResetPasswordRoute(val email: String)
