plugins {
    id("amber.messenger.compose")
}

dependencies {
    commonMainImplementation(projects.modules.core.arch)
    commonMainImplementation(projects.modules.core.uikit)
    commonMainImplementation(projects.modules.feature.counter.ui)
    commonMainImplementation(projects.modules.feature.counter.core)
    commonMainApi(projects.modules.core.database)
    commonMainApi(libs.koin.core)
    commonMainImplementation(libs.koin.compose)
}
