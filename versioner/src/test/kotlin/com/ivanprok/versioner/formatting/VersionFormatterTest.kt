package com.ivanprok.versioner.formatting

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class VersionFormatterTest {
    private val defaultFormat = createVersionFormat()
    private val masterFormat = createVersionFormat("master", "%tag%")
    private val featureFormat = createVersionFormat("feature/.*", "%tag%%.count%%-branch_name%")
    private val formatter = VersionFormatter(
        mapOf("master" to masterFormat, "feature/.*" to featureFormat),
        defaultFormat
    )

    private val masterVersion = createVersion(
        "1.2.3",
        branchName = BRANCH_MASTER
    )

    private val featureVersion = createVersion(
        "1.2.3",
        15,
        branchName =  BRANCH_FEATURE
    )

    private val otherVersion = createVersion(
        "1.2.3",
        15,
        branchName = BRANCH_OTHER
    )

    private val masterFormattedVersion = FormattedVersion("1.2.3", 102003)
    private val featureFormattedVersion = FormattedVersion("1.2.3.15-$BRANCH_FEATURE", 102003)
    private val otherFormattedVersion = FormattedVersion("1.2.3.15", 102003)

    @Test
    fun `Version formatter formats version with correct provided format`() {
        assertEquals(featureFormattedVersion, formatter.format(featureVersion))
    }

    @Test
    fun `When there is no suitable format formatter formats with default format`() {
        assertEquals(otherFormattedVersion, formatter.format(otherVersion))
    }

    fun `Version formatter selects first suitable format`() {
        TODO()
    }

    companion object {
        private const val BRANCH_MASTER = "master"
        private const val BRANCH_FEATURE = "feature/PROJ-123_some_feature_branch"
        private const val BRANCH_OTHER = "release/1.2.3"
    }

}
