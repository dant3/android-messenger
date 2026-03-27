plugins {
    id("amber.messenger.compose")
}

compose.resources {
    publicResClass = true
}

dependencies {
    commonMainApi(libs.compose.material3)
    commonMainApi(libs.compose.material.icons.extended)
    commonMainApi(libs.compose.resources)
    commonMainApi(libs.koin.core)
    commonMainApi(libs.jetbrains.navigation.compose)
    commonMainApi(libs.material3.navigation.kmp)
}