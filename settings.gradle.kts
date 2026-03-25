pluginManagement {
    includeBuild("gradle/build-logic")

    repositories {
        gradlePluginPortal()
        mavenCentral()
        google {
            content {
                includeGroupByRegex(".*google.*")
                includeGroupByRegex(".*android.*")
            }
        }
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
            name = "JetBrains Compose Repository"
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        maven(rootProject.projectDir.resolve("libs")) {
            content {
                includeGroup("org.webrtc")
            }
        }
        maven("https://jitpack.io") {
            content {
                includeGroupByRegex("com\\.github\\..*")
            }
        }
        google {
            content {
                includeGroupByRegex(".*google.*")
                includeGroupByRegex(".*android.*")
            }
        }
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "Amber"

include(":androidApp")
include(":desktopApp")
include(":iosApp")

include(":modules:core:arch")
include(":modules:core:utils")
include(":modules:ui:uikit")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
