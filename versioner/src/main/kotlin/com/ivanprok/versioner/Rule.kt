package com.ivanprok.versioner

import com.ivanprok.versioner.utils.gradleProperty
import org.gradle.api.Project

/**
 * Правило вычисления версии.
 */
class Rule(
    /**
     * Ветки, для которых будет выполняться данное правило версионирования.
     */
    val branches: List<String>,
    project: Project
) {
    /**
     * Шаблон для формирования строковой версии.
     * По умолчанию имеет значение [DEFAULT_NAME_PATTERN]
     *
     * @see com.ivanprok.versioner.formatting.NamePattern
     */
    var namePattern by gradleProperty(project, DEFAULT_NAME_PATTERN)

    /**
     * Шаблон для формирования численной версии.
     * По умолчанию имеет значение [DEFAULT_CODE_PATTERN]
     *
     * @see com.ivanprok.versioner.formatting.CodePattern
     */
    var codePattern by gradleProperty(project, DEFAULT_CODE_PATTERN)

    /**
     * Базовое значение от которого будет отсчитываться численная версия.
     * По умолчанию имеет значение [DEFAULT_BASE_CODE]
     */
    var baseCode by gradleProperty(project, DEFAULT_BASE_CODE)

    /**
     * Длина выводимого в численной версии hash последнего коммита.
     * По умолчанию имеет значение [DEFAULT_COMMIT_HASH_LENGTH]
     */
    var commitHashLength by gradleProperty(project, DEFAULT_COMMIT_HASH_LENGTH)

    /**
     * Флаг, определяющий будет ли наличие неотслеживаемых изменений влиять на
     * определение текущей версии как "грязной".
     * По умолчанию имеет значение [DEFAULT_UNTRACKED_IS_DIRTY]
     */
    var untrackedIsDirty by gradleProperty(project, DEFAULT_UNTRACKED_IS_DIRTY)
}
