package com.ivanprok.versioner

import com.ivanprok.versioner.git.GitInfo
import com.ivanprok.versioner.git.Tag
import org.eclipse.jgit.lib.ObjectId

class GitInfoToVersionMapper {
    fun map(gitInfo: GitInfo) = with(gitInfo) {
        Version(
                findSuitableSemVerTag(lastTags),
                commitsSinceLastTag,
                lastCommit?.sha.orEmpty(),
                branchName,
                hasUncommittedChanges,
                hasUntrackedChanges
        )
    }

    private fun findSuitableSemVerTag(availableTags: List<Tag>): SemVerTag = availableTags
            .map { SemVerTag.parse(it.name) }
            .toSet()
            .max()
            ?: SemVerTag.UNKNOWN

    private val ObjectId.sha: String
        get() = ObjectId.toString(this)
}
