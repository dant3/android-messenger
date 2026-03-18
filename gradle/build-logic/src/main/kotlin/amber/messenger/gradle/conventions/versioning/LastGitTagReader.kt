package amber.messenger.gradle.conventions.versioning

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Project

object LastGitTagReader {
    fun findLastGitTag(project: Project): LastGitTag? =
        runCatching { findLastGitTagFromRepository(project) }
            .onFailure { project.logger.warn("Failed to read last git tag: ${it.message}") }
            .getOrNull()

    private fun findLastGitTagFromRepository(project: Project): LastGitTag? {
        val repository = FileRepositoryBuilder()
            .setWorkTree(project.rootProject.rootDir)
            .readEnvironment() // scan environment GIT_* variables
            .findGitDir() // scan up the file system tree
            .build()
        val git = Git(repository)
        val tags = git.tagList().call().groupBy {
            it.objectId
        }

        RevWalk(repository).use { revWalk ->
            var distance = 0
            revWalk.markStart(revWalk.parseCommit(repository.resolve("HEAD")))
            var next = revWalk.next()
            while (next != null) {
                val tag = tags.get(next)
                if (tag != null) {
                    val lastTag = tag.first().name.replace("refs/tags/", "")
                    return LastGitTag(lastTag, distance, next.abbreviate(7).name())
                }
                next = revWalk.next()
                distance++
            }
            return null
        }
    }
}
