package com.ivanprok.versioner.formatting

import com.ivanprok.versioner.Version

class VersionFormatter(
    private val formats: Map<String, VersionFormat>,
    private val defaultFormat: VersionFormat
) {

    fun format(version: Version) = findSuitableFormat(version.branchName)
            .format(version)

    private fun findSuitableFormat(branchName: String): VersionFormat = formats
            .toList()
            .find { branchName.matches(regex(it.first)) }
            ?.second
            ?: defaultFormat

    private fun regex(pattern: String) = Regex(if (pattern.isBlank()) ".*" else pattern)
}
