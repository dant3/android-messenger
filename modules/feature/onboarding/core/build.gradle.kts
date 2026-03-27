plugins {
    id("amber.messenger.multiplatform")
}

dependencies {
    commonMainApi(projects.modules.core.arch)
    commonMainImplementation(libs.koin.core)

    commonTestImplementation(libs.bundles.testing.kotest)
    commonTestImplementation(libs.bundles.testing.koin)
    jvmTestImplementation(libs.kotest.runner.junit5)
}
