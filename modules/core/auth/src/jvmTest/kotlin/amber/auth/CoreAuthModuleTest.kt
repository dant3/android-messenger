package amber.auth

import io.kotest.core.spec.style.FunSpec
import io.kotest.koin.KoinExtension
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlin.getValue
import org.koin.test.KoinTest
import org.koin.test.inject

class CoreAuthModuleTest : KoinTest, FunSpec() {
    init {
        extension(KoinExtension(modules = listOf(CoreAuthModule)))

        val tokenStorage: AuthTokenStorage by inject()
        val stateProvider: AuthStateProvider by inject()

        test("AuthTokenStorage resolves to AuthManager") {
            tokenStorage.shouldBeInstanceOf<AuthManager>()
        }

        test("AuthStateProvider resolves to AuthManager") {
            stateProvider.shouldBeInstanceOf<AuthManager>()
        }
    }
}
