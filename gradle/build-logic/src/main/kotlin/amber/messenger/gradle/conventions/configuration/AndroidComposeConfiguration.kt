package amber.messenger.gradle.conventions.configuration

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.withType
import amber.messenger.gradle.conventions.configuration.ComposeConfiguration.configureCompose

object AndroidComposeConfiguration {
    fun Project.configureAndroidCompose() {
        project.plugins.withId(libs.findPlugin("kotlin.compose.compiler").get().get().pluginId) {
            configureCompose()
        }
        project.plugins.withId(libs.findPlugin("paparazzi").get().get().pluginId) {
            tasks["check"].dependsOn("verifyPaparazziDebug")
            // Workaround: Paparazzi 2.0.0-alpha04 uses internal Gradle APIs for HTML report
            // generation that changed in Gradle 9.3+. Disable HTML reports until Paparazzi is fixed.
            tasks.withType<Test> {
                reports.html.required.set(false)
            }
        }

        androidCommonConfiguration {
            with(buildFeatures) {
                compose = true
            }
        }

        dependencies {
            "commonMainApi"(libs.findLibrary("compose.runtime").get())
            "debugImplementation"(libs.findLibrary("compose.ui.tooling").get())
            "debugImplementation"(libs.findLibrary("compose.ui.tooling.preview").get())
        }
    }
}
