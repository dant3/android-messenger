plugins {
    id("amber.messenger.multiplatform")
}

dependencies {
    commonMainImplementation(projects.modules.core.analytics)
    commonMainImplementation(projects.modules.core.arch)
    commonMainImplementation(projects.modules.core.auth)
    commonMainImplementation(projects.modules.core.client.impl)
    commonMainImplementation(projects.modules.core.database)
    commonMainImplementation(projects.modules.core.firebase)
    commonMainImplementation(projects.modules.core.logger)
    commonMainImplementation(projects.modules.core.mediaPlayer)
    commonMainImplementation(projects.modules.core.networkMonitor)
    commonMainImplementation(projects.modules.core.powerMonitor)
    commonMainImplementation(projects.modules.core.preferences)
    commonMainImplementation(projects.modules.core.webrtc)
    commonMainImplementation(projects.modules.feature.counter.core)
    commonMainImplementation(projects.modules.feature.counter.ui)
    commonMainImplementation(projects.modules.feature.onboarding.core)
    commonMainImplementation(projects.modules.feature.onboarding.ui)
    commonMainImplementation(projects.modules.ui)
    commonMainApi(libs.koin.core)
}
