package amber.feature.onboarding.core

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnboardingStore {
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    fun nextStep() {
        _state.update { current ->
            val nextIndex = current.currentStep + 1
            if (nextIndex < OnboardingStep.entries.size) {
                current.copy(currentStep = nextIndex)
            } else {
                current.copy(completed = true)
            }
        }
    }

    fun previousStep() {
        _state.update { current ->
            if (current.currentStep > 0) {
                current.copy(currentStep = current.currentStep - 1)
            } else {
                current
            }
        }
    }

    fun complete() {
        _state.update { it.copy(completed = true) }
    }
}

data class OnboardingState(
    val currentStep: Int = 0,
    val completed: Boolean = false,
) {
    val step: OnboardingStep get() = OnboardingStep.entries[currentStep]
    val isFirstStep: Boolean get() = currentStep == 0
    val isLastStep: Boolean get() = currentStep == OnboardingStep.entries.size - 1
}

enum class OnboardingStep(
    val title: String,
    val description: String,
) {
    Welcome(
        title = "Welcome",
        description = "Welcome to Amber Messenger, your secure messaging app.",
    ),
    Features(
        title = "Features",
        description = "Send messages, share media, and stay connected with friends and family.",
    ),
    GetStarted(
        title = "Get Started",
        description = "Create an account or sign in to begin messaging.",
    ),
}
