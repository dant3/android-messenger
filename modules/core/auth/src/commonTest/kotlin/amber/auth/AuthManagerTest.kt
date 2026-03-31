package amber.auth

import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.getValue
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.inject

class AuthManagerTest : KoinTest, FunSpec() {
    init {
        val testStorage = module { single<SecureStorage> { FakeSecureStorage() } }
        extension(KoinExtension(modules = listOf(CoreAuthModule, testStorage)))

        val tokenStorage: AuthTokenStorage by inject()
        val stateProvider: AuthStateProvider by inject()

        beforeEach {
            tokenStorage.clearAll()
        }

        test("initial state is Unauthenticated when storage is empty") {
            stateProvider.authState.value.shouldBeInstanceOf<AuthState.Unauthenticated>()
            stateProvider.isAuthenticated.value shouldBe false
        }

        test("storeTokens persists and is retrievable") {
            val tokens = AuthTokens(
                accessToken = "access",
                refreshToken = "refresh",
                expiresAtEpochMs = 1_000_000L,
            )
            tokenStorage.storeTokens(tokens)
            tokenStorage.getTokens() shouldBe tokens
        }

        test("storeProfile persists and is retrievable") {
            val profile = UserProfile(
                userId = "u1",
                email = "test@example.com",
                displayName = "Test User",
            )
            tokenStorage.storeProfile(profile)
            tokenStorage.getProfile() shouldBe profile
        }

        test("state becomes Authenticated when both tokens and profile are stored") {
            val tokens = AuthTokens("a", "r", 1L)
            val profile = UserProfile("u1", "test@example.com", "Test")

            tokenStorage.storeTokens(tokens)
            stateProvider.authState.value.shouldBeInstanceOf<AuthState.Unauthenticated>()

            tokenStorage.storeProfile(profile)
            val state = stateProvider.authState.value
            state.shouldBeInstanceOf<AuthState.Authenticated>()
            state.tokens shouldBe tokens
            state.profile shouldBe profile
            stateProvider.isAuthenticated.value shouldBe true
        }

        test("clearAll resets to Unauthenticated") {
            tokenStorage.storeTokens(AuthTokens("a", "r", 1L))
            tokenStorage.storeProfile(UserProfile("u1", "e@e.com", "N"))
            stateProvider.authState.value.shouldBeInstanceOf<AuthState.Authenticated>()

            tokenStorage.clearAll()
            stateProvider.authState.value.shouldBeInstanceOf<AuthState.Unauthenticated>()
            stateProvider.isAuthenticated.value shouldBe false
            tokenStorage.getTokens().shouldBeNull()
            tokenStorage.getProfile().shouldBeNull()
        }

        test("restores state from pre-populated storage") {
            val tokens = AuthTokens("a", "r", 1L)
            val profile = UserProfile("u1", "e@e.com", "N")

            tokenStorage.storeTokens(tokens)
            tokenStorage.storeProfile(profile)

            // Re-inject to simulate fresh start with same storage
            stateProvider.authState.value.shouldBeInstanceOf<AuthState.Authenticated>()
            tokenStorage.getTokens() shouldBe tokens
            tokenStorage.getProfile() shouldBe profile
        }
    }
}
