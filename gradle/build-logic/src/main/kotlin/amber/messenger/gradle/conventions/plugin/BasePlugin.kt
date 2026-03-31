package amber.messenger.gradle.conventions.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import amber.messenger.gradle.conventions.versioning.ProjectVersionConfiguration.configureProjectVersion

class BasePlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        configureProjectVersion()
    }
}
