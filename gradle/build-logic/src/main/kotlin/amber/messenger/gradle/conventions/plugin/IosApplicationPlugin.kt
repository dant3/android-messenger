package amber.messenger.gradle.conventions.plugin

import amber.messenger.gradle.conventions.configuration.ComposeConfiguration.configureCompose
import amber.messenger.gradle.conventions.configuration.DetektConfiguration.configureDetekt
import amber.messenger.gradle.conventions.configuration.IosSimulatorTasks.configureIosSimulatorTasks
import amber.messenger.gradle.conventions.configuration.KotlinConfiguration.configureKotlin
import amber.messenger.gradle.conventions.configuration.composeCompilerPluginId
import io.gitlab.arturbosch.detekt.DetektPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

class IosApplicationPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("org.jetbrains.compose")
            apply(composeCompilerPluginId)
            apply(DetektPlugin::class.java)
            apply("idea")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            listOf(
                iosArm64(),
                iosSimulatorArm64(),
            ).forEach { target ->
                target.binaries.framework {
                    baseName = "IosApp"
                    isStatic = true
                    linkerOpts("-lsqlite3")
                }
            }
        }

        configureKotlin()
        configureDetekt()
        configureCompose()
        configureNativeCompilerOptions()
        configureIosSimulatorTasks()
    }

    private fun Project.configureNativeCompilerOptions() {
        tasks.withType<KotlinNativeCompile> {
            compilerOptions {
                allWarningsAsErrors.set(true)
                freeCompilerArgs.addAll(
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                )
            }
        }
    }
}
