plugins {
    id("amber.messenger.multiplatform")
}

dependencies {
    commonMainImplementation(projects.modules.core.arch)
    commonMainImplementation(projects.modules.core.database)
    commonMainImplementation(projects.modules.core.mediaPlayer)
    commonMainImplementation(projects.modules.core.preferences)
    commonMainImplementation(projects.modules.feature.counter.core)
    commonMainImplementation(projects.modules.feature.counter.ui)
    commonMainImplementation(projects.modules.feature.onboarding.core)
    commonMainImplementation(projects.modules.feature.onboarding.ui)
    commonMainImplementation(projects.modules.ui)
    commonMainApi(libs.koin.core)
}
