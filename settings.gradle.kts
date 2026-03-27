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
include(":modules:core:database")
include(":modules:core:navigation")
include(":modules:core:preferences")
include(":modules:core:image-loading")
include(":modules:core:media-player")
include(":modules:core:uikit")
include(":modules:core:utils")
include(":modules:runtime")
include(":modules:ui")

include(":modules:feature:counter:core")
include(":modules:feature:counter:ui")

include(":modules:feature:onboarding:core")
include(":modules:feature:onboarding:ui")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
