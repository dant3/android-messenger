plugins {
    id("amber.messenger.compose")
    alias(libs.plugins.mokkery)
}

dependencies {
    commonMainApi(libs.kmp.logger)
    commonMainImplementation(libs.koin.core)

    commonTestImplementation(libs.bundles.testing.kotest)
    commonTestImplementation(libs.bundles.testing.koin)
    jvmTestImplementation(libs.kotest.runner.junit5)
}
