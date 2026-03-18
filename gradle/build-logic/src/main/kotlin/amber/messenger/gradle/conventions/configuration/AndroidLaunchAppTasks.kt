package amber.messenger.gradle.conventions.configuration

import java.util.Locale
import org.gradle.api.Project

object AndroidLaunchAppTasks {
    fun Project.configureAndroidLauncherTasks() {
        androidApplicationComponents {
            onVariants { variant ->
                val variantNameCapitalized = variant.name.replaceFirstChar {
                    when {
                        it.isLowerCase() -> it.titlecase(Locale.getDefault())
                        else -> it.toString()
                    }
                }
                tasks.register("open$variantNameCapitalized") {
                    dependsOn(tasks.named("install$variantNameCapitalized"))

                    doLast {
                        providers.exec {
                            commandLine = "adb shell monkey -p ${variant.applicationId} -c android.intent.category.LAUNCHER 1".split(" ")
                        }
                    }
                }
            }
        }
    }
}
