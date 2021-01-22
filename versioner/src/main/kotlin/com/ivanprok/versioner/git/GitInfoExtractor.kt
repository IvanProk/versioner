package com.ivanprok.versioner.git

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Constants
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.gradle.api.Project

class GitInfoExtractor {

    fun extractGitInfo(project: Project): GitInfo? = findRepository(project)
        ?.let(::buildGitInfo)

    private fun buildGitInfo(repository: Repository): GitInfo? = GitInfo.Builder().apply {
        val git = Git.wrap(repository)
        val head = repository.findRef(Constants.HEAD)

        branchName = repository.branch
        lastCommit = head.objectId
        lastTags = getLastTags(repository, head, getTags(git))
        val status = git.status().call()
        hasUncommittedChanges = !status.isClean
        hasUntrackedChanges = status.untracked.isNotEmpty()
        description = git.describe().setTags(true).call().orEmpty()

    }.build()

    private fun getLastTags(repository: Repository, head: Ref, tags: List<Tag>): List<Tag> {
        val walk = RevWalk(repository)
        walk.markStart(walk.parseCommit(head.objectId))
        var commitTags: List<Tag> = emptyList()
        for (commit in walk) {
            val tagsHere = tags.filter { it.objectId == commit }
            if (tagsHere.isNotEmpty()) {
                commitTags = tagsHere
                break
            }
        }
        return commitTags
    }

    private fun getTags(git: Git): List<Tag> = git.tagList().call()
        .map { git.nameRev().add(it.objectId).call().toList() }
        .flatten()
        .map { (objectId, readableRef) ->
            Tag(readableRef, objectId)
        }

    private fun findRepository(project: Project): Repository? {
        return try {
            FileRepositoryBuilder()
                .readEnvironment()
                .findGitDir(project.projectDir)
                .build()
        } catch (e: IllegalArgumentException) {
            project.logger.error("[Versioner]: Repository not found!")
            null
        }
    }
}
