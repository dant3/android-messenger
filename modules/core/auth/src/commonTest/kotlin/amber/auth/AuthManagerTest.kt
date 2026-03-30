package amber.auth

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

class AuthManagerTest : FunSpec({
    lateinit var storage: FakeSecureStorage
    lateinit var manager: AuthManager

    beforeEach {
        storage = FakeSecureStorage()
        manager = AuthManager(storage)
    }

    test("initial state is Unauthenticated when storage is empty") {
        manager.authState.value.shouldBeInstanceOf<AuthState.Unauthenticated>()
        manager.isAuthenticated.value shouldBe false
    }

    test("storeTokens persists and is retrievable") {
        val tokens = AuthTokens(
            accessToken = "access",
            refreshToken = "refresh",
            expiresAtEpochMs = 1_000_000L,
        )
        manager.storeTokens(tokens)
        manager.getTokens() shouldBe tokens
    }

    test("storeProfile persists and is retrievable") {
        val profile = UserProfile(
            userId = "u1",
            email = "test@example.com",
            displayName = "Test User",
        )
        manager.storeProfile(profile)
        manager.getProfile() shouldBe profile
    }

    test("state becomes Authenticated when both tokens and profile are stored") {
        val tokens = AuthTokens("a", "r", 1L)
        val profile = UserProfile("u1", "test@example.com", "Test")

        manager.storeTokens(tokens)
        manager.authState.value.shouldBeInstanceOf<AuthState.Unauthenticated>()

        manager.storeProfile(profile)
        val state = manager.authState.value
        state.shouldBeInstanceOf<AuthState.Authenticated>()
        state.tokens shouldBe tokens
        state.profile shouldBe profile
        manager.isAuthenticated.value shouldBe true
    }

    test("clearAll resets to Unauthenticated") {
        manager.storeTokens(AuthTokens("a", "r", 1L))
        manager.storeProfile(UserProfile("u1", "e@e.com", "N"))
        manager.authState.value.shouldBeInstanceOf<AuthState.Authenticated>()

        manager.clearAll()
        manager.authState.value.shouldBeInstanceOf<AuthState.Unauthenticated>()
        manager.isAuthenticated.value shouldBe false
        manager.getTokens().shouldBeNull()
        manager.getProfile().shouldBeNull()
    }

    test("AuthManager restores state from pre-populated storage") {
        val tokens = AuthTokens("a", "r", 1L)
        val profile = UserProfile("u1", "e@e.com", "N")

        val first = AuthManager(storage)
        first.storeTokens(tokens)
        first.storeProfile(profile)

        val second = AuthManager(storage)
        second.authState.value.shouldBeInstanceOf<AuthState.Authenticated>()
        second.getTokens() shouldBe tokens
        second.getProfile() shouldBe profile
    }
})
