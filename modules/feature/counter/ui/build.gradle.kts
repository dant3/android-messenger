plugins {
    id("amber.messenger.compose")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainApi(projects.modules.feature.counter.core)
    commonMainImplementation(projects.modules.core.navigation)
    commonMainImplementation(projects.modules.core.uikit)
    commonMainImplementation(libs.koin.compose)
    commonMainImplementation(libs.coil.compose)
}
