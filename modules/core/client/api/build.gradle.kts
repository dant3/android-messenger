plugins {
    id("amber.messenger.multiplatform")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    commonMainApi(libs.kotlinx.coroutines.core)
    commonMainApi(libs.kotlinx.serialization.json)
}
