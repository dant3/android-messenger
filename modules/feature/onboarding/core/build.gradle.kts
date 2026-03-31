plugins {
    id("amber.messenger.multiplatform")
    alias(libs.plugins.mokkery)
}

dependencies {
    commonMainApi(projects.modules.core.arch)
    commonMainImplementation(projects.modules.core.auth)
    commonMainImplementation(libs.koin.core)

    commonTestImplementation(libs.bundles.testing.kotest)
    commonTestImplementation(libs.bundles.testing.koin)
}
