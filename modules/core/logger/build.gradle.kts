plugins {
    id("amber.messenger.multiplatform")
}

dependencies {
    commonMainImplementation(projects.modules.core.arch)
    commonMainImplementation(projects.modules.core.utils)
    commonMainImplementation(libs.koin.core)
}
