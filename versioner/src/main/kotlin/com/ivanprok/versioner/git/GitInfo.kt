package com.ivanprok.versioner.git

import org.eclipse.jgit.lib.ObjectId


data class GitInfo(
    val branchName: String,
    val lastTags: List<Tag>,
    val lastCommit: ObjectId?,
    val commitsSinceLastTag: Int,
    val hasUncommittedChanges: Boolean,
    val hasUntrackedChanges: Boolean,
    val description: String
) {
    class Builder {
        var branchName: String = ""
        var lastTags: List<Tag> = emptyList()
        var lastCommit: ObjectId? = null
        var commitsSinceLastTag: Int = 0
        var hasUncommittedChanges: Boolean = false
        var hasUntrackedChanges: Boolean = false
        var description: String = ""

        fun build() = GitInfo(
            branchName,
            lastTags,
            lastCommit,
            commitsSinceLastTag,
            hasUncommittedChanges,
            hasUntrackedChanges,
            description
        )
    }
}
