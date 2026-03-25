plugins {
    id("amber.messenger.multiplatform")
    alias(libs.plugins.sqldelight)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.sqldelight.android.driver)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.sqldelight.jvm.driver)
            }
        }
        iosMain {
            dependencies {
                implementation(libs.sqldelight.native.driver)
            }
        }
    }
}

dependencies {
    commonMainApi(projects.modules.core.utils)
    commonMainImplementation(libs.bundles.sqldelight)
    commonMainImplementation(libs.koin.core)

    commonTestImplementation(libs.bundles.testing.kotest)
    jvmTestImplementation(libs.kotest.runner.junit5)
}
