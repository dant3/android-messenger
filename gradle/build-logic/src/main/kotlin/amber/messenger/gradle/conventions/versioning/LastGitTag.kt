package amber.messenger.gradle.conventions.versioning

data class LastGitTag(
    val tagName: String,
    val commitDistance: Int,
    val gitHash: String,
)
