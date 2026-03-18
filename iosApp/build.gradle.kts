plugins {
    id("amber.messenger.ios.application")
}

kotlin {
    sourceSets {
        iosMain {
            dependencies {
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
            }
        }
    }
}
