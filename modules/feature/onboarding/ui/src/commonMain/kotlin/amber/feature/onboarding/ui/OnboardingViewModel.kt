package amber.feature.onboarding.ui

import amber.feature.onboarding.core.usecase.ConfirmEmailUseCase
import amber.feature.onboarding.core.usecase.ForgotPasswordUseCase
import amber.feature.onboarding.core.usecase.LoginUseCase
import amber.feature.onboarding.core.usecase.RegisterUseCase
import amber.feature.onboarding.core.usecase.ResetPasswordUseCase
import amber.feature.onboarding.core.usecase.SetPasswordUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val registerUseCase: RegisterUseCase,
    private val confirmEmailUseCase: ConfirmEmailUseCase,
    private val setPasswordUseCase: SetPasswordUseCase,
    private val loginUseCase: LoginUseCase,
    private val forgotPasswordUseCase: ForgotPasswordUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(OnboardingUiState())
    val state: StateFlow<OnboardingUiState> = _state.asStateFlow()

    fun updateEmail(email: String) {
        _state.update { it.copy(email = email) }
    }

    fun updatePassword(password: String) {
        _state.update { it.copy(password = password) }
    }

    fun updateConfirmationCode(code: String) {
        _state.update { it.copy(confirmationCode = code) }
    }

    fun updateNewPassword(newPassword: String) {
        _state.update { it.copy(newPassword = newPassword) }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }

    fun register(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            registerUseCase.register(_state.value.email)
                .onSuccess { onSuccess() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun confirmEmail(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            confirmEmailUseCase.confirmEmail(email, _state.value.confirmationCode)
                .onSuccess { onSuccess() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun setPassword(email: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            setPasswordUseCase.setPassword(email, _state.value.password)
                .onSuccess { onComplete() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun login(onComplete: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            loginUseCase.login(_state.value.email, _state.value.password)
                .onSuccess { onComplete() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun forgotPassword(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            forgotPasswordUseCase.requestPasswordReset(_state.value.email)
                .onSuccess { onSuccess() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
            _state.update { it.copy(isLoading = false) }
        }
    }

    fun resetPassword(email: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            resetPasswordUseCase.resetPassword(email, _state.value.confirmationCode, _state.value.newPassword)
                .onSuccess { onComplete() }
                .onFailure { e -> _state.update { it.copy(error = e.message) } }
            _state.update { it.copy(isLoading = false) }
        }
    }
}
