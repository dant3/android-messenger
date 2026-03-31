package amber.messenger.gradle.conventions.configuration

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinBaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import amber.messenger.gradle.conventions.configuration.JavaConfiguration.configureJvmTests

object KotlinConfiguration {
    fun Project.configureKotlin() {
        configureJvmToolchain()
        configureJvmTests()

        tasks.withType<KotlinCompile> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_21)
                allWarningsAsErrors.set(true)
                javaParameters.set(true)
                freeCompilerArgs.addAll(
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-opt-in=kotlinx.coroutines.FlowPreview",
                    "-Xannotation-default-target=param-property",
                )
            }
        }

        dependencies {
            // we use coroutines almost everywhere anyways, but it's really necessary only because of -opt-in flags
            // see https://youtrack.jetbrains.com/issue/KT-48419
            "commonMainImplementation"(libs.findLibrary("kotlinx.coroutines.core").get())
        }

        // kotest JUnit 5 runner is needed for all JVM tests; added lazily because jvm() target
        // may be declared after configureKotlin() runs
        val kotestRunner = libs.findLibrary("kotest.runner.junit5").get()
        configurations.matching { it.name == "jvmTestImplementation" }.configureEach {
            project.dependencies.add(name, kotestRunner)
        }
    }

    private fun Project.configureJvmToolchain() {
        (extensions.getByName("kotlin") as KotlinBaseExtension).jvmToolchain(21)
    }
}
