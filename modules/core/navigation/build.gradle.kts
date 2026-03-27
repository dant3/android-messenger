plugins {
    id("amber.messenger.compose")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainApi(libs.koin.core)
    commonMainApi(libs.kotlinx.serialization.core)
    commonMainApi(libs.jetbrains.navigation.compose)
}
