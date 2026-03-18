package amber.messenger.gradle.conventions.plugin

import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import amber.messenger.gradle.conventions.configuration.ComposeConfiguration.configureCompose
import amber.messenger.gradle.conventions.configuration.DetektConfiguration.configureDetekt
import amber.messenger.gradle.conventions.configuration.KotlinConfiguration.configureKotlin
import amber.messenger.gradle.conventions.configuration.composeCompilerPluginId

class DesktopApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("org.jetbrains.compose")
            apply(composeCompilerPluginId)
            apply(DetektPlugin::class.java)
            apply("idea")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            jvm()
        }

        configureKotlin()
        configureDetekt()
        configureCompose()
    }
}
