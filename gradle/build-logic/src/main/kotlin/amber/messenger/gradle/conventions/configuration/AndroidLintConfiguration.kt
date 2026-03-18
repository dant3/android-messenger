package amber.messenger.gradle.conventions.configuration

import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

object AndroidLintConfiguration {
    fun Project.configureLint() {
        androidCommonConfiguration {
            with(lint) {
                val htmlReport = layout.buildDirectory.file("reports/lint.html")
                val txtReport = layout.buildDirectory.file("reports/lint.txt")
                val lintConfigFile = rootProject.layout.projectDirectory.file("gradle/lint.xml")

                lintConfig = lintConfigFile.asFile
                abortOnError = true
                htmlOutput = htmlReport.get().asFile
                textOutput = txtReport.get().asFile
                explainIssues = true
                showAll = true
                ignoreTestSources = true
                checkGeneratedSources = false
                checkDependencies = true
            }
        }

        dependencies {
            "lintChecks"(libs.findLibrary("compose.lint.checks").get())
        }
    }
}
