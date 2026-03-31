package amber.messenger.gradle.conventions.plugin

import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import amber.messenger.gradle.conventions.configuration.AndroidCommonConfiguration.configureAndroid
import amber.messenger.gradle.conventions.configuration.AndroidComposeConfiguration.configureAndroidCompose
import amber.messenger.gradle.conventions.configuration.AndroidLaunchAppTasks.configureAndroidLauncherTasks
import amber.messenger.gradle.conventions.configuration.DetektConfiguration.configureDetekt
import amber.messenger.gradle.conventions.configuration.KotlinConfiguration.configureKotlin
import amber.messenger.gradle.conventions.configuration.composeCompilerPluginId

class AndroidApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(BasePlugin::class.java)
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.application")
            apply("org.gradle.android.cache-fix")
            apply("kotlin-parcelize")
            apply("idea")
            apply(DetektPlugin::class.java)
            apply(composeCompilerPluginId)
            apply("org.jetbrains.compose")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget()
        }

        configureAndroid()
        configureKotlin()
        configureDetekt()
        configureAndroidCompose()
        configureAndroidLauncherTasks()
    }
}
