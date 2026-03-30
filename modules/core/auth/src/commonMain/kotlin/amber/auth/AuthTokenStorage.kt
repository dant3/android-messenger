package amber.auth

interface AuthTokenStorage {
    fun getTokens(): AuthTokens?
    fun storeTokens(tokens: AuthTokens)
    fun getProfile(): UserProfile?
    fun storeProfile(profile: UserProfile)
    fun clearAll()
}
