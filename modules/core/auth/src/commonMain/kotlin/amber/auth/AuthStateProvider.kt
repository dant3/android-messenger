package amber.auth

import kotlinx.coroutines.flow.StateFlow

interface AuthStateProvider {
    val authState: StateFlow<AuthState>
    val isAuthenticated: StateFlow<Boolean>
}
