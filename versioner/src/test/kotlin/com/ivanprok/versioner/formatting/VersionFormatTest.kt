package com.ivanprok.versioner.formatting

import com.ivanprok.versioner.SemVerTag
import com.ivanprok.versioner.Version
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class VersionFormatTest {
    private val format = createVersionFormat()

    private val version = Version(
        SemVerTag("1.2.3", 1, 2, 3),
        15,
        "13876aebf376f89c81c9d9dde33122",
        "feature/PROJ-123_some_feature_branch",
        hasUncommittedChanges = false,
        hasUntrackedChanges = false
    )

    private val formattedVersion = FormattedVersion("1.2.3.15-feature/PROJ-123_some_feature_branch", 102003)

    @Test
    fun `Format is right`() {
        // TODO: Рассмотреть другие параметры для формата (hash, commits count, untracked and uncommitted changes)
        assertEquals(format.format(version), formattedVersion)
    }
}
