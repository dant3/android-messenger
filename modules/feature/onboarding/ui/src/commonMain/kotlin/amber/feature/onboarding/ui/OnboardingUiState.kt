package amber.feature.onboarding.ui

data class OnboardingUiState(
    val email: String = "",
    val password: String = "",
    val confirmationCode: String = "",
    val newPassword: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val completed: Boolean = false,
)
