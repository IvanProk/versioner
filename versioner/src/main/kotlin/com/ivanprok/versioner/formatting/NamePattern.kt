package com.ivanprok.versioner.formatting

import org.gradle.api.GradleException
import org.intellij.lang.annotations.Language
import java.text.ParseException
import kotlin.IllegalArgumentException
/**
 * Шаблон для предаствления версии в строковом формате.
 *
 * Поддерживаемые компоненты:
 * ```
 * |   Компонент    | Значение                                                                  |
 * |:-------------:	|---------------------------------------------------------------------------|
 * |     `tag`     	| git tag                                                                   |
 * |    `count`    	| Число коммитов после тэга                                                 |
 * |  `commit_sha` 	| sha hash последнего коммита                                               |
 * | `branch_name` 	| название текущей ветки                                                    |
 * |    `dirty`    	| флаг указывающий на наличие незакоммиченных или неотслеживаемых изменений |
 * ```
 *
 * Каждый из компонентов должен начинаться и заканчиваться символом `%`. Также внутри компоненты могут быть
 * указаны символы разделители `.` или `-`. Например, при нахождении на коммите `a55cbd2c` в ветке master, опережающем
 * последний тэг на 2 коммита и наличии незакоммиченных изменений,
 * по формату `%tag%%.count%%-branch_name%%-commit_sha%%-dirty%` будет сформирована версия:
 * `0.1.2.2-master-a55cbd2c-dirty`
 */
class NamePattern constructor(val components: Set<Component>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NamePattern

        if (components != other.components) return false

        return true
    }

    override fun hashCode(): Int {
        return components.hashCode()
    }

    companion object {
        private const val COMPONENT_MARKER = '%'

        private const val COMPONENT_PATTERN = "$COMPONENT_MARKER([^%]+)$COMPONENT_MARKER"
        fun compile(pattern: String) = Regex(COMPONENT_PATTERN)
            .findAll(pattern)
            .mapTo(mutableSetOf()) {
                Component.parse(it.groupValues[1])
            }.let { NamePattern(it) }
    }

    data class Component(val unescapedRaw: String, val type: Type) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Component

            if (unescapedRaw != other.unescapedRaw) return false
            if (type != other.type) return false

            return true
        }

        override fun hashCode(): Int {
            var result = unescapedRaw.hashCode()
            result = 31 * result + type.hashCode()
            return result
        }

        companion object {
            fun parse(unescapedRaw: String): Component {
                val key = unescapedRaw.replace(Regex("\\W"), "")
                val type = Type.values().find { it.key == key }
                    ?: throw FormatException("Unparseable pattern component: $key")
                return Component(unescapedRaw, type)
            }
        }

        enum class Type(val key: String) {
            TAG("tag"),
            COUNT("count"),
            BRANCH_NAME("branch_name"),
            COMMIT_SHA("commit_sha"),
            DIRTY("dirty");
        }
    }
}
