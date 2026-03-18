package amber.messenger.gradle.conventions.configuration

import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension

object ComposeConfiguration {
    fun Project.configureCompose() {
        extensions.configure<ComposeCompilerGradlePluginExtension> {
            stabilityConfigurationFiles.add { file("$rootDir/gradle/compose-stability.conf") }
        }
    }
}
