package com.ivanprok.versioner


import com.ivanprok.versioner.formatting.FormattedVersion
import com.ivanprok.versioner.formatting.VersionFormatter
import com.ivanprok.versioner.git.GitInfoExtractor
import org.gradle.api.Project

class VersionProvider(
    private val gitInfoExtractor: GitInfoExtractor,
    private val versionFormatter: VersionFormatter,
    private val gitInfoToVersionMapper: GitInfoToVersionMapper
) {
    fun getFormattedVersion(project: Project): FormattedVersion? = gitInfoExtractor
            .extractGitInfo(project)
            ?.let { gitInfoToVersionMapper.map(it) }
            ?.let { versionFormatter.format(it) }
}
