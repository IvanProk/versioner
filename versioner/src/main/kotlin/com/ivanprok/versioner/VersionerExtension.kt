package com.ivanprok.versioner

import com.ivanprok.versioner.formatting.CodePattern
import com.ivanprok.versioner.formatting.NamePattern
import com.ivanprok.versioner.formatting.VersionFormat
import com.ivanprok.versioner.formatting.VersionFormatter
import com.ivanprok.versioner.git.GitInfoExtractor
import com.ivanprok.versioner.utils.gradleProperty
import groovy.lang.Closure
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionContainer

open class VersionerExtension(private val project: Project) {

    var namePattern by gradleProperty(project, DEFAULT_NAME_PATTERN)
    var codePattern by gradleProperty(project, DEFAULT_CODE_PATTERN)
    var baseCode by gradleProperty(project, DEFAULT_BASE_CODE)
    var untrackedIsDirty by gradleProperty(project, DEFAULT_UNTRACKED_IS_DIRTY)
    var commitHashLength by gradleProperty(project, DEFAULT_COMMIT_HASH_LENGTH)

    val versionName: String
        get() = createVersionProvider().getFormattedVersion(project)?.name ?: VERSION_NAME_UNKNOWN

    val versionCode: Int
        get() = createVersionProvider().getFormattedVersion(project)?.code ?: VERSION_CODE_UNKNOWN


    private val rules = project.container(Rule::class.java) {
        Rule(listOf(it), project)
    }

    fun forBranches(vararg branches: String, configurator: Rule.() -> Unit) {
        rules.add(Rule(branches.toList(), project).apply(configurator))
    }

    private fun createVersionProvider() = VersionProvider(
            GitInfoExtractor(),
            createVersionFormatter(),
            GitInfoToVersionMapper()
    )

    private fun createVersionFormatter() = rules
            .takeIf { it.isNotEmpty() }
            ?.groupBy { it.branches.joinToString("|") }
            ?.mapValues { RuleToFormatMapper().map(it.value.last()) }
            .let { VersionFormatter(it ?: emptyMap(), createDefaultFormat()) }

    private fun createDefaultFormat() = VersionFormat(
            NamePattern.compile(namePattern),
            CodePattern.compile(codePattern),
            baseCode,
            commitHashLength,
            untrackedIsDirty
    )

    companion object {

    }
}

val ExtensionContainer.versioner
    get() = getByName(PLUGIN_EXTENSION_NAME) as VersionerExtension

internal typealias RulesContainer = NamedDomainObjectContainer<Rule>
