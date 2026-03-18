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

compose.desktop {
    application {
        mainClass = "amber.messenger.desktop.MainKt"
    }
}
