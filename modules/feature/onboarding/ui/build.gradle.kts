plugins {
    id("amber.messenger.compose")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.mokkery)
}

dependencies {
    commonMainApi(projects.modules.feature.onboarding.core)
    commonMainImplementation(projects.modules.core.navigation)
    commonMainImplementation(projects.modules.core.uikit)
    commonMainImplementation(libs.koin.compose)
    commonMainImplementation(libs.koin.compose.viewmodel)
    commonMainImplementation(libs.koin.compose.viewmodel.navigation)
    commonMainImplementation(libs.jetbrains.lifecycle.viewmodel.compose)

    commonTestImplementation(libs.bundles.testing.kotest)
    commonTestImplementation(libs.bundles.testing.koin)
    jvmTestImplementation(libs.kotest.runner.junit5)
}
