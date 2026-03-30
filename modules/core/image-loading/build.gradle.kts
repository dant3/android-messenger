plugins {
    id("amber.messenger.multiplatform")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    sourceSets {
        androidMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.coil.gif)
                implementation(libs.coil.video)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(libs.javacv.platform)
            }
        }
        iosMain {
            dependencies {
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}

dependencies {
    commonMainApi(libs.coil.core)
    commonMainImplementation(projects.modules.core.arch)
    commonMainImplementation(libs.coil.network.ktor3)
    commonMainImplementation(libs.coil.svg)
    commonMainImplementation(libs.ktor.client.core)
    commonMainImplementation(libs.koin.core)
}
