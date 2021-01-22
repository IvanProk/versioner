package com.ivanprok.versioner.formatting

import com.ivanprok.versioner.SemVerTag
import com.ivanprok.versioner.Version

internal fun createVersionFormat(
    name: String = "default",
    namePattern: String = "%tag%%.count%",
    codePattern: String = "MMNNPPP",
    baseCode: Int = 0,
    commitHashLength: Int = 7,
    untrackedIsDirty: Boolean = false
) = VersionFormat(
    NamePattern.compile(namePattern),
    CodePattern.compile(codePattern),
    baseCode,
    commitHashLength,
    untrackedIsDirty
)

internal fun createVersion(
    tag: String = "1.2.3",
    commitsCount: Int = 0,
    lastCommitSha: String = "some_sha",
    branchName: String = "master",
    hasUncommittedChanges: Boolean = false,
    hasUntrackedChanges: Boolean = false
) = Version(
    SemVerTag.parse(tag),
    commitsCount,
    lastCommitSha,
    branchName,
    hasUncommittedChanges,
    hasUntrackedChanges
)
