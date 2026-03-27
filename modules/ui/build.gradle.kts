plugins {
    id("amber.messenger.compose")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainImplementation(projects.modules.core.arch)
    commonMainImplementation(projects.modules.core.navigation)
    commonMainImplementation(projects.modules.core.imageLoading)
    commonMainImplementation(projects.modules.core.uikit)
    commonMainImplementation(projects.modules.core.preferences)
    commonMainImplementation(libs.koin.compose)
    commonMainImplementation(libs.koin.compose.viewmodel)
    commonMainImplementation(libs.koin.compose.viewmodel.navigation)
    commonMainImplementation(libs.jetbrains.lifecycle.viewmodel.compose)
    commonMainImplementation(libs.coil.compose)
}
