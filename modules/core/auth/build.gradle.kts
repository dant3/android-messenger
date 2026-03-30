plugins {
    id("amber.messenger.multiplatform")
    alias(libs.plugins.kotlin.serialization)
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
    jvmTestImplementation(libs.bundles.testing.junit5)
    jvmTestImplementation(libs.bundles.testing.koin)
}
