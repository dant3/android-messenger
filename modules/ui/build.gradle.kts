plugins {
    id("amber.messenger.compose")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(projects.modules.core.arch)
    commonMainImplementation(projects.modules.core.navigation)
    commonMainImplementation(projects.modules.core.uikit)
    commonMainImplementation(projects.modules.feature.counter.ui)
    commonMainImplementation(projects.modules.feature.counter.core)
    commonMainImplementation(projects.modules.feature.onboarding.ui)
    commonMainImplementation(projects.modules.feature.onboarding.core)
    commonMainApi(projects.modules.core.database)
    commonMainImplementation(projects.modules.core.preferences)
    commonMainApi(libs.koin.core)
    commonMainImplementation(libs.koin.compose)
    commonMainImplementation(libs.koin.compose.viewmodel)
    commonMainImplementation(libs.koin.compose.viewmodel.navigation)
    commonMainImplementation(libs.jetbrains.lifecycle.viewmodel.compose)
    commonMainImplementation(libs.kotlinx.serialization.core)
}
