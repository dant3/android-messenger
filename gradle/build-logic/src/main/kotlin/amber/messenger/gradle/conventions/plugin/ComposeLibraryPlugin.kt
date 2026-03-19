package amber.messenger.gradle.conventions.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile

class ComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(AndroidComposeLibraryPlugin::class.java)
        }

        extensions.configure<KotlinMultiplatformExtension> {
            jvm()
            iosArm64()
            iosSimulatorArm64()
        }

        configureNativeCompilerOptions()
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
