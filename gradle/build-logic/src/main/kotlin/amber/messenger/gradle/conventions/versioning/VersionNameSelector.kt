package amber.messenger.gradle.conventions.versioning

import com.vdurmont.semver4j.Semver
import com.vdurmont.semver4j.SemverException
import org.gradle.api.Project

object VersionNameSelector {
    fun getReleaseName(
        project: Project,
        gitInfo: GitInfo,
    ): String {
        val branchNameForVersion = getBranchNameForVersion(gitInfo.branch)
        return getMainVersionName(project, branchNameForVersion)
    }

    private fun getBranchNameForVersion(branchName: String?): String? {
        if (branchName == null || branchName == "master") return null
        val split = branchName.split(Regex("(?i)android-"), limit = 2)
        return when {
            split.size > 1 -> "ANDROID-" + split[1]
            else -> null
        }
    }

    private fun getMainVersionName(project: Project, branchName: String?): String {
        val gitVersionInfo = LastGitTagReader.findLastGitTag(project) ?: return "unknown"
        try {
            val version = Semver(gitVersionInfo.tagName)

            val selectedVersion = when {
                gitVersionInfo.commitDistance == 0 -> version
                else ->
                    version
                        .nextMinor()
                        .withSuffix("SNAPSHOT")
                        .withBuild(branchName ?: gitVersionInfo.gitHash)
            }

            return selectedVersion.toString()
        } catch (ex: SemverException) {
            project.logger.error("Last git tag $gitVersionInfo does not contain valid version according to semver spec: ", ex)
            return "unknown"
        }
    }
}
