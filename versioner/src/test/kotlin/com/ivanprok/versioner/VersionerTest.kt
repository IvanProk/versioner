package com.ivanprok.versioner

import org.gradle.internal.impldep.org.eclipse.jgit.api.Git
import org.gradle.internal.impldep.org.eclipse.jgit.lib.AnyObjectId
import org.gradle.internal.impldep.org.junit.rules.TemporaryFolder
import org.gradle.internal.impldep.org.testng.annotations.AfterTest
import org.gradle.testfixtures.ProjectBuilder
import java.io.File

abstract class VersionerTest {
    val projectFolder by lazy {
        TemporaryFolder().apply {
            create()
            newFile("build.gradle")
        }
    }

    val project by lazy {
        ProjectBuilder.builder()
            .withProjectDir(projectFolder.root)
            .build()
    }

    val git
        get() = Git.init().setDirectory(projectFolder.root).call()

    val plugin by lazy {
        project.pluginManager.apply("com.ivanprok.versioner")
        val extension = project.extensions.getByName("versioner")
        assert(extension is VersionerExtension)
        extension as VersionerExtension
    }

    fun makeChange() {
        File(projectFolder.root, "build.gradle").appendText("// addition")
    }

    fun addCommit() {
        makeChange()
        git.add().addFilepattern("build.gradle").call()
        git.commit().setMessage("addition").call()
    }

    fun addTag(name: String, lightWeight: Boolean = false) {
        git.tag().setName(name).setAnnotated(!lightWeight).call()
    }

    fun addBranch(name: String) {
        git.checkout().setCreateBranch(true).setName(name).call()
    }

    fun checkout(branch: String) {
        git.checkout().setName(branch).call()
    }

    fun merge(from: AnyObjectId) {
        git.merge().setCommit(true).include(from).call()
    }

    @AfterTest
    fun afterTest() {
        projectFolder.delete()
    }
}
