package amber.messenger.gradle.conventions.versioning

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Project

object ChangelogProvider {
    private const val MASTER_BRANCH = "master"
    private const val MAX_COMMITS = 50

    fun getChangelog(project: Project): List<CommitInfo> {
        val repository = FileRepositoryBuilder()
            .setWorkTree(project.rootProject.rootDir)
            .readEnvironment()
            .findGitDir()
            .build()

        return repository.use { repo ->
            val git = Git(repo)
            val currentBranch = repo.branch
            val headIsTagged = isHeadTagged(repo, git)

            if (currentBranch == MASTER_BRANCH || headIsTagged) {
                getCommitsSinceLastTag(repo, git)
            } else {
                getCommitsComparedToMaster(repo)
            }
        }
    }

    private fun isHeadTagged(repository: Repository, git: Git): Boolean {
        val headObjectId = repository.resolve("HEAD")
        val tags = git.tagList().call()
        return tags.any { it.objectId == headObjectId }
    }

    private fun getCommitsSinceLastTag(repository: Repository, git: Git): List<CommitInfo> {
        val tags = git.tagList().call().groupBy { it.objectId }

        RevWalk(repository).use { revWalk ->
            val commits = mutableListOf<CommitInfo>()
            val headObjectId = repository.resolve("HEAD")
            revWalk.markStart(revWalk.parseCommit(headObjectId))

            // If HEAD is tagged, we're at a release commit.
            // Include commits since the previous tag, not this one.
            val headIsTagged = tags.containsKey(headObjectId)
            var isFirstCommit = true

            var next = revWalk.next()
            while (next != null && commits.size < MAX_COMMITS) {
                // Stop at a tagged commit, unless it's HEAD and HEAD is tagged
                // (in that case, we want commits since the previous tag)
                if (tags.containsKey(next) && !(isFirstCommit && headIsTagged)) {
                    break
                }
                commits.add(next.toCommitInfo())
                isFirstCommit = false
                next = revWalk.next()
            }
            return commits
        }
    }

    private fun getCommitsComparedToMaster(repository: Repository): List<CommitInfo> {
        val masterRef = repository.resolve("origin/$MASTER_BRANCH")
            ?: repository.resolve(MASTER_BRANCH)
            ?: return emptyList()

        RevWalk(repository).use { revWalk ->
            val headCommit = revWalk.parseCommit(repository.resolve("HEAD"))
            val masterCommit = revWalk.parseCommit(masterRef)

            revWalk.markStart(headCommit)
            revWalk.markUninteresting(masterCommit)

            return revWalk.asSequence()
                .take(MAX_COMMITS)
                .map { it.toCommitInfo() }
                .toList()
        }
    }

    private fun RevCommit.toCommitInfo(): CommitInfo = CommitInfo(
        sha = abbreviate(7).name(),
        title = shortMessage,
        author = authorIdent.name,
    )

    data class CommitInfo(
        val sha: String,
        val title: String,
        val author: String,
    ) {
        fun toMarkdown(): String = "• `$sha` $title — _${author}_"
    }
}

fun List<ChangelogProvider.CommitInfo>.toMarkdown(): String =
    joinToString("\n") { it.toMarkdown() }
