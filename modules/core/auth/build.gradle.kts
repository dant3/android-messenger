plugins {
    id("amber.messenger.multiplatform")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotest)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

dependencies {
    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.kotlinx.coroutines.core)
    commonMainImplementation(libs.kotlinx.serialization.json)

    androidMainImplementation(libs.androidx.security.crypto)

    commonTestImplementation(libs.bundles.testing.kotest)
    commonTestImplementation(libs.bundles.testing.koin)
    jvmTestImplementation(libs.kotest.runner.junit5)
}
