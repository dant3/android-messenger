package amber.messenger.gradle.conventions.configuration

import com.android.build.api.dsl.LibraryExtension
import com.android.build.api.dsl.ManagedVirtualDevice
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.tasks.testing.Test
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import amber.messenger.gradle.conventions.configuration.AndroidLintConfiguration.configureLint

object AndroidCommonConfiguration {
    fun Project.configureAndroid() {
        val proguardRulesFile = file("proguard-rules.pro")

        androidCommonConfiguration {
            namespace = makeProjectNamespace(project)
            compileSdk = 36

            defaultConfig.apply {
                minSdk = 28
                vectorDrawables.useSupportLibrary = true
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                testInstrumentationRunnerArguments["clearPackageData"] = "true"
            }

            buildTypes.getByName("release") {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    proguardRulesFile,
                )
            }

            compileOptions.apply {
                sourceCompatibility = JavaVersion.VERSION_21
                targetCompatibility = JavaVersion.VERSION_21
                isCoreLibraryDesugaringEnabled = true
            }

            with(buildFeatures) {
                buildConfig = false
                aidl = false
                resValues = false
                shaders = false
            }

            testOptions.apply {
                execution = "ANDROIDX_TEST_ORCHESTRATOR"
                unitTests.apply {
                    isIncludeAndroidResources = true
                    isReturnDefaultValues = true
                    // See https://github.com/robolectric/robolectric/issues/4534
                    all {
                        it.systemProperty("user.home", System.getenv("WORKSPACE") ?: System.getenv("HOME"))
                        /*
                         * Override target sdk for Robolectric tests to 35 due to using Robolectric 4.15
                         * Its impossible to upgrade to 4.16 due to:
                         * - https://github.com/robolectric/robolectric/issues/10586
                         * - https://github.com/cashapp/paparazzi/issues/1979
                         */
                        it.systemProperty("robolectric.sdk", "35")
                        // Increase max heap size for Compose tests to prevent OOM issues
                        it.maxHeapSize = "2048m"
                    }
                }

                reportDir = layout.buildDirectory.dir("reports").get().toString()
                resultsDir = layout.buildDirectory.dir("test-results").get().toString()

                managedDevices.apply {
                    with(allDevices) {
                        create<ManagedVirtualDevice>("api28") {
                            device = "Pixel 3"
                            apiLevel = 28
                            systemImageSource = "aosp"
                        }
                        create<ManagedVirtualDevice>("api36") {
                            device = "Pixel 6"
                            apiLevel = 36
                            systemImageSource = "aosp"
                        }
                    }
                }
            }

            packaging.apply {
                resources.apply {
                    excludes += listOf(
                        "**.dll",
                        "META-INF/licenses/**",
                        "META-INF/AL2.0",
                        "META-INF/LGPL2.1",
                        "META-INF/*.md",
                    )
                }
            }
        }

        // consumerProguardFiles is only available on library modules
        project.plugins.withType(LibraryPlugin::class.java) {
            project.extensions.configure(LibraryExtension::class.java) {
                defaultConfig.apply {
                    if (proguardRulesFile.exists()) {
                        consumerProguardFiles.add(proguardRulesFile)
                    }
                }
            }
        }

        disableAndroidUnitTestsOn("release")

        dependencies {
            // https://developer.android.com/studio/write/java8-support
            "coreLibraryDesugaring"(libs.findLibrary("tools.desugarjdklibs").get())
            "androidTestImplementation"(libs.findLibrary("androidx.test.runner").get())
            "androidTestUtil"(libs.findLibrary("androidx.test.orchestrator").get())
        }

        tasks.withType<Test> {
            // this check doesn't work well with Android because of generated R files
            failOnNoDiscoveredTests.set(false)
        }

        configureLint()
    }

    private fun makeProjectNamespace(project: Project): String {
        val projectsChain = generateSequence(project) {
            it.parent?.takeUnless { it == project.rootProject }
        }.toList().asReversed()

        return "amber.messenger." + projectsChain.joinToString(".") { it.name.replace('-', '.').lowercase() }
    }
}
