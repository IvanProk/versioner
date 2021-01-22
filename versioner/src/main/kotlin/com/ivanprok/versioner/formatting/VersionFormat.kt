package com.ivanprok.versioner.formatting

import com.ivanprok.versioner.Version

class VersionFormat(
    private val namePattern: NamePattern,
    private val codePattern: CodePattern,
    private val baseCode: Int,
    private val commitHashLength: Int,
    private val untrackedIsDirty: Boolean
) {
    fun format(version: Version): FormattedVersion = FormattedVersion(
            formatName(version),
            formatCode(version)
    )

    private fun formatName(version: Version): String = with(version) {
        namePattern.components.joinToString("") {
            val value = when (it.type) {
                NamePattern.Component.Type.TAG -> tag.raw
                NamePattern.Component.Type.COUNT -> "$commitsCount"
                NamePattern.Component.Type.BRANCH_NAME -> branchName
                NamePattern.Component.Type.COMMIT_SHA -> lastCommitSha.substring(0, commitHashLength)
                NamePattern.Component.Type.DIRTY -> if (hasUncommittedChanges || untrackedIsDirty && hasUntrackedChanges) "dirty" else ""
            }
            it.unescapedRaw.replace(it.type.key, value)
        }
    }

    private fun formatCode(version: Version): Int = version.tag
            .run {
                codePattern.components.joinToString("") {
                    val value = when (it.type) {
                        CodePattern.Component.Type.MAJOR -> major
                        CodePattern.Component.Type.MINOR -> minor
                        CodePattern.Component.Type.PATCH -> patch
                        CodePattern.Component.Type.BUILD -> version.commitsCount
                        else -> 0
                    }
                    String.format("%0${it.multiplier}d", value).substring(0, it.multiplier)
                }.toInt()
            } + baseCode
}
