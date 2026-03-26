package amber.messenger.gradle.conventions.configuration

import org.gradle.api.Project
import org.gradle.api.tasks.Exec

object IosSimulatorTasks {
    private const val DEFAULT_SIMULATOR = "iPhone Air"
    private const val XCODE_SCHEME = "iosApp"

    fun Project.configureIosSimulatorTasks() {
        val simulator = providers.gradleProperty("iosSimulator")
            .orElse(DEFAULT_SIMULATOR)
        val scheme = providers.gradleProperty("iosScheme")
            .orElse(XCODE_SCHEME)
        val derivedData = layout.buildDirectory.dir("xcode-derived")

        val bootSimulator = tasks.register("bootSimulator", Exec::class.java) {
            group = "ios"
            description = "Boot the iOS Simulator"
            commandLine("xcrun", "simctl", "boot", simulator.get())
            isIgnoreExitValue = true
        }

        val buildXcode = tasks.register("buildXcode", Exec::class.java) {
            group = "ios"
            description = "Build the Xcode project for iOS Simulator"
            dependsOn(tasks.named("linkDebugFrameworkIosSimulatorArm64"))
            workingDir(projectDir)
            commandLine(
                "xcodebuild",
                "-project", "${projectDir}/iosApp.xcodeproj",
                "-scheme", scheme.get(),
                "-destination", "platform=iOS Simulator,name=${simulator.get()}",
                "-derivedDataPath", derivedData.get().asFile.absolutePath,
                "build",
            )
        }

        val installSimulator = tasks.register("installSimulator", Exec::class.java) {
            group = "ios"
            description = "Install the app on the booted Simulator"
            dependsOn(bootSimulator, buildXcode)
            commandLine(
                "xcrun", "simctl", "install", "booted",
                "${derivedData.get().asFile.absolutePath}/Build/Products/Debug-iphonesimulator/iosApp.app",
            )
        }

        tasks.register("openSimulator", Exec::class.java) {
            group = "ios"
            description = "Launch the app on the iOS Simulator"
            dependsOn(installSimulator)
            commandLine("xcrun", "simctl", "launch", "booted", "amber.messenger.ios")
        }
    }
}
