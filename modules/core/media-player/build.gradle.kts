plugins {
    id("amber.messenger.compose")
}

configurations.configureEach {
    resolutionStrategy.eachDependency {
        if (requested.group.startsWith("org.jetbrains.compose")) {
            useVersion(libs.versions.composeMultiplatformVersion.get())
        }
    }
}

dependencies {
    commonMainApi(libs.compose.media.player)
    commonMainImplementation(libs.koin.core)
}
