package amber.feature.onboarding.core.usecase.dummy

import amber.auth.AuthTokenStorage
import amber.auth.AuthTokens
import amber.auth.UserProfile

internal fun AuthTokenStorage.storeDummyAuth(email: String) {
    storeTokens(
        AuthTokens(
            accessToken = "dummy-access-token",
            refreshToken = "dummy-refresh-token",
            expiresAtEpochMs = Long.MAX_VALUE,
        ),
    )
    storeProfile(
        UserProfile(
            userId = "dummy-user",
            email = email,
            displayName = email.substringBefore("@"),
        ),
    )
}
