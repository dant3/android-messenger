package amber.messenger.gradle.conventions.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import amber.messenger.gradle.conventions.configuration.AndroidComposeConfiguration.configureAndroidCompose
import amber.messenger.gradle.conventions.configuration.composeCompilerPluginId

class AndroidComposeLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply(AndroidLibraryPlugin::class.java)
            apply(composeCompilerPluginId)
            apply("org.jetbrains.compose")
        }

        configureAndroidCompose()
    }
}
