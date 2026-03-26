plugins {
    id("amber.messenger.compose")
}

dependencies {
    commonMainApi(projects.modules.feature.counter.core)
    commonMainImplementation(projects.modules.core.uikit)
    commonMainImplementation(libs.koin.core)
}
