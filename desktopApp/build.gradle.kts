plugins {
    id("amber.messenger.desktop.application")
}

kotlin {
    sourceSets {
        jvmMain {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(libs.compose.material3)
            }
        }
    }
}

dependencies {
    jvmMainImplementation(libs.koin.core)
    jvmMainImplementation(projects.modules.core.arch)
    jvmMainImplementation(projects.modules.ui)
}

compose.desktop {
    application {
        mainClass = "amber.messenger.desktop.MainKt"

        nativeDistributions {
            packageName = "Amber Messenger"

            macOS {
                iconFile = file("src/jvmMain/resources/app-icon.icns")
            }
            windows {
                iconFile = file("src/jvmMain/resources/app-icon.ico")
            }
            linux {
                iconFile = file("src/jvmMain/resources/app-icon.png")
            }
        }
    }
}
