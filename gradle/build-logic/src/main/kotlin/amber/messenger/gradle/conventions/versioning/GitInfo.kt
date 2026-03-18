package amber.messenger.gradle.conventions.versioning

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Project

data class GitInfo(
    val branch: String,
    val commit: String,
) {
    companion object {
        val UNKNOWN = GitInfo(branch = "UNKNOWN", commit = "UNKNOWN")

        fun load(project: Project): GitInfo {
            return loadFromEnvironment()
                ?: runCatching { loadFromGit(project) }
                    .onFailure { project.logger.warn("Failed to load git info: ${it.message}") }
                    .getOrNull()
                ?: UNKNOWN
        }

        fun loadFromEnvironment(): GitInfo? {
            val env = System.getenv()

            return GitInfo(
                branch = env["GIT_BRANCH"] ?: return null,
                commit = env["GIT_COMMIT"] ?: return null,
            )
        }

        fun loadFromGit(project: Project): GitInfo? {
            val repository = FileRepositoryBuilder()
                .setWorkTree(project.rootProject.rootDir)
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build()
            val git = Git(repository)

            return GitInfo(
                branch = repository.branch,
                commit = git.log().setMaxCount(1).call().firstOrNull()?.abbreviate(7)?.name() ?: return null,
            )
        }
    }
}