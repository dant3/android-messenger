plugins {
    id("amber.messenger.multiplatform")
    alias(libs.plugins.sqldelight)
}

sqldelight {
    databases {
        create("CounterDatabase") {
            packageName.set("amber.feature.counter.core.db")
        }
    }
}

dependencies {
    commonMainApi(projects.modules.core.database)
    commonMainApi(projects.modules.core.arch)
    commonMainImplementation(libs.bundles.sqldelight)
    commonMainImplementation(libs.koin.core)

    commonTestImplementation(libs.bundles.testing.kotest)
    commonTestImplementation(libs.bundles.testing.koin)
    jvmTestImplementation(libs.kotest.runner.junit5)
    jvmTestImplementation(libs.sqldelight.jvm.driver)
}
