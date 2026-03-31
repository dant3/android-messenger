plugins {
    id("amber.messenger.multiplatform")
    id("org.jetbrains.kotlin.native.cocoapods")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xexpect-actual-classes")
    }

    cocoapods {
        summary = "Firebase integration module"
        homepage = "https://github.com/AmberId/amber-messenger"
        ios.deploymentTarget = "16.0"

        pod("FirebaseCore")
        pod("FirebaseAnalytics")
        pod("FirebaseCrashlytics")
    }
}

dependencies {
    commonMainImplementation(projects.modules.core.analytics)
    commonMainImplementation(projects.modules.core.arch)
    commonMainImplementation(projects.modules.core.utils)
    commonMainImplementation(libs.koin.core)
    androidMainImplementation(platform(libs.firebase.platform))
    androidMainImplementation(libs.firebase.analytics)
    androidMainImplementation(libs.firebase.crashlytics)
}
