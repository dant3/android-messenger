plugins {
    alias(libs.plugins.cacheFixPlugin)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.lint) apply false
    alias(libs.plugins.android.test) apply false
    alias(libs.plugins.androidx.baselineprofile) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    alias(libs.plugins.kotlin.compose.compiler) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.paparazzi) apply false
    alias(libs.plugins.detekt) apply false
}

val testReport = tasks.register<TestReport>("testReport") {
    val testTasks = allprojects.flatMap { it.tasks.withType<Test>() }
    testResults.from(testTasks)
    val buildDir = project.layout.buildDirectory.dir("reports/tests")
    destinationDirectory.set(buildDir)

    mustRunAfter(testTasks)

    doLast {
        logger.lifecycle("Test report is collected in {}", destinationDirectory.get())
    }
}
