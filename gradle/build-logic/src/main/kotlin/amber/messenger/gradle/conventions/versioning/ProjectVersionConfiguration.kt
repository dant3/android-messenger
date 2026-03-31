package amber.messenger.gradle.conventions.versioning

import org.gradle.api.Project

object ProjectVersionConfiguration {
    fun Project.configureProjectVersion() {
        val gitInfo = GitInfo.load(this)
        version = VersionNameSelector.getReleaseName(this, gitInfo)
    }
}
