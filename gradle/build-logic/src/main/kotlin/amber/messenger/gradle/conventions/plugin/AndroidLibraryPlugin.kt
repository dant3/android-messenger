package amber.messenger.gradle.conventions.plugin

import com.android.build.api.dsl.LibraryExtension
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import amber.messenger.gradle.conventions.configuration.AndroidCommonConfiguration.configureAndroid
import amber.messenger.gradle.conventions.configuration.DetektConfiguration.configureDetekt
import amber.messenger.gradle.conventions.configuration.KotlinConfiguration.configureKotlin
class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(BasePlugin::class.java)
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.library")
            apply("org.gradle.android.cache-fix")
            apply("kotlin-parcelize")
            apply("idea")
            apply(DetektPlugin::class.java)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            androidTarget()
        }

        configureAndroid()
        configureKotlin()
        configureDetekt()

        extensions.configure<LibraryExtension> {
            testFixtures.enable = true
        }
    }
}
