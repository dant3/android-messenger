plugins {
    id("amber.messenger.compose")
}

dependencies {
    commonMainApi(libs.compose.material3)
    commonMainApi(libs.compose.material.icons.extended)
    commonMainImplementation(libs.compose.resources)
}