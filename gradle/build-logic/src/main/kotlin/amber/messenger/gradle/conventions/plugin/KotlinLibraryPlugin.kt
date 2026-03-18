package amber.messenger.gradle.conventions.plugin

import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import amber.messenger.gradle.conventions.configuration.DetektConfiguration.configureDetekt
import amber.messenger.gradle.conventions.configuration.KotlinConfiguration.configureKotlin

class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply(DetektPlugin::class.java)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            jvm()
        }

        configureKotlin()
        configureDetekt()
    }
}
