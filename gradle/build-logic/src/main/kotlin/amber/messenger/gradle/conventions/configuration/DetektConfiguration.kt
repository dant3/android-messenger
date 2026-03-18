package amber.messenger.gradle.conventions.configuration

import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

object DetektConfiguration {
    fun Project.configureDetekt() {
        dependencies {
            "detektPlugins"(libs.findLibrary("gradlePlugin.detekt.formatting").get())
        }

        val shouldUseAutomaticCorrection = property("detekt.autoCorrect")?.toString()?.toBoolean() ?: false
        val shouldUseParallelAnalysis = property("detekt.parallel")?.toString()?.toBoolean() ?: false

        extensions.configure<DetektExtension> {
            buildUponDefaultConfig = true
            allRules = false
            ignoreFailures = shouldUseAutomaticCorrection
            autoCorrect = shouldUseAutomaticCorrection
            config.setFrom("$rootDir/gradle/detekt.yml")
            parallel = shouldUseParallelAnalysis
        }
    }
}
