package amber.messenger.gradle.conventions.configuration

import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.withType

object JavaConfiguration {
    fun Project.configureJvmTests() {
        tasks.withType<Test> {
            useJUnitPlatform()
            systemProperty("kotlinx.coroutines.test.default_timeout", "60s")
            systemProperty("junit.jupiter.execution.timeout.testable.method.default", "60s")
            systemProperty("junit.jupiter.execution.timeout.lifecycle.method.default", "60s")
            jvmArgs("-XX:+EnableDynamicAgentLoading")

            testLogging {
                events("skipped", "failed")
                exceptionFormat = TestExceptionFormat.FULL
                showExceptions = true
                showCauses = true
                showStackTraces = true
            }
            reports {
                junitXml.apply {
                    isOutputPerTestCase = true
                    mergeReruns = true
                }
            }

            maxParallelForks = 1
            forkEvery = 100
            maxHeapSize = "2g"
            minHeapSize = "512m"
        }
    }
}
