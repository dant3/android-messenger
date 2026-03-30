package amber.auth

sealed interface AuthState {
    data class Authenticated(
        val tokens: AuthTokens,
        val profile: UserProfile,
    ) : AuthState

    data object Unauthenticated : AuthState
}
