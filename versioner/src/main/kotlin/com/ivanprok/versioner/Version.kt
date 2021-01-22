package com.ivanprok.versioner

data class Version(
    val tag: SemVerTag,
    val commitsCount: Int,
    val lastCommitSha: String,
    val branchName: String,
    val hasUncommittedChanges: Boolean,
    val hasUntrackedChanges: Boolean
)
