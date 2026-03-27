plugins {
    id("amber.messenger.multiplatform")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }
}

dependencies {
    commonMainApi(libs.multiplatform.settings)
    commonMainApi(libs.multiplatform.settings.coroutines)
    commonMainImplementation(libs.koin.core)
    commonMainImplementation(libs.kotlinx.coroutines.core)
}
