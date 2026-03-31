package amber.auth

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json

internal class AuthManager(
    private val secureStorage: SecureStorage,
) : AuthTokenStorage, AuthStateProvider {
    private val json = Json { ignoreUnknownKeys = true }

    private val _authState = MutableStateFlow(loadAuthState())
    override val authState: StateFlow<AuthState> = _authState.asStateFlow()

    private val _isAuthenticated = MutableStateFlow(_authState.value is AuthState.Authenticated)
    override val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

    override fun getTokens(): AuthTokens? {
        val raw = secureStorage.getString(KEY_TOKENS) ?: return null
        return json.decodeFromString<AuthTokens>(raw)
    }

    override fun storeTokens(tokens: AuthTokens) {
        secureStorage.putString(KEY_TOKENS, json.encodeToString(AuthTokens.serializer(), tokens))
        updateState()
    }

    override fun getProfile(): UserProfile? {
        val raw = secureStorage.getString(KEY_PROFILE) ?: return null
        return json.decodeFromString<UserProfile>(raw)
    }

    override fun storeProfile(profile: UserProfile) {
        secureStorage.putString(KEY_PROFILE, json.encodeToString(UserProfile.serializer(), profile))
        updateState()
    }

    override fun clearAll() {
        secureStorage.clear()
        updateState()
    }

    private fun loadAuthState(): AuthState {
        val tokens = getTokens()
        val profile = getProfile()
        return if (tokens != null && profile != null) {
            AuthState.Authenticated(tokens, profile)
        } else {
            AuthState.Unauthenticated
        }
    }

    private fun updateState() {
        val state = loadAuthState()
        _authState.value = state
        _isAuthenticated.value = state is AuthState.Authenticated
    }

    private companion object {
        const val KEY_TOKENS = "auth_tokens"
        const val KEY_PROFILE = "auth_profile"
    }
}
