
plugins {
    `kotlin-dsl`
    alias(libs.plugins.detekt)
    kotlin("plugin.serialization") version embeddedKotlinVersion
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
dependencies {
    compileOnly(libs.gradlePlugin.android)
    compileOnly(libs.gradlePlugin.detekt)
    compileOnly(libs.gradlePlugin.kotlin)
    compileOnly(libs.gradlePlugin.kotlin.compose.compiler)
    compileOnly(libs.gradlePlugin.compose.multiplatform)
    implementation(libs.jgit)
    implementation(libs.semver4j)
    implementation(libs.kotlinx.serialization.json)
}

gradlePlugin {
    plugins {
        register("kotlinLibrary") {
            id = "amber.messenger.kotlin"
            implementationClass = "amber.messenger.gradle.conventions.plugin.KotlinLibraryPlugin"
        }
    }
    plugins {
        register("androidLibrary") {
            id = "amber.messenger.android.library"
            implementationClass = "amber.messenger.gradle.conventions.plugin.AndroidLibraryPlugin"
        }
    }
    plugins {
        register("androidComposeLibrary") {
            id = "amber.messenger.android.library.compose"
            implementationClass = "amber.messenger.gradle.conventions.plugin.AndroidComposeLibraryPlugin"
        }
    }
    plugins {
        register("androidApplication") {
            id = "amber.messenger.android.application"
            implementationClass = "amber.messenger.gradle.conventions.plugin.AndroidApplicationPlugin"
        }
    }
    plugins {
        register("desktopApplication") {
            id = "amber.messenger.desktop.application"
            implementationClass = "amber.messenger.gradle.conventions.plugin.DesktopApplicationPlugin"
        }
    }
    plugins {
        register("iosApplication") {
            id = "amber.messenger.ios.application"
            implementationClass = "amber.messenger.gradle.conventions.plugin.IosApplicationPlugin"
        }
    }
}
