package amber.auth

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val userId: String,
    val email: String,
    val displayName: String,
)
