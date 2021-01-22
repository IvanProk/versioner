package com.ivanprok.versioner.formatting

/**
 * Шаблон для представления версии в числовом формате.
 *
 * Поддерживаемые символы:
 * ```
 * | Символ | Значение                              |
 * |--------|---------------------------------------|
 * |   `M`  | Мажорная версия                       |
 * |   `N`  | Минорная версия                       |
 * |   `P`  | Патч                                  |
 * |   `B`  | Количество коммитов с последнего тега |
 * ```
 *
 * Количество указанных символов будет соответствовать количеству разрядов числа представляющего компонент версии.
 * Например, `MMNNNPPP` будет говорить о том, что мажорная версия будет описана двумя разрядами, минорная версия и патч
 * тремя разрядами. То есть при тэге `123.4567.890` числовое представление версии будет равно `12456890`.
 */
class CodePattern constructor(val components: Set<Component>) {

    companion object {
        private const val MAX_ALLOWED_LENGTH = 9
        fun compile(pattern: String): CodePattern {
            val totalLength = pattern.length
            if (totalLength > MAX_ALLOWED_LENGTH)
                throw FormatException("Provided code pattern $pattern is too long. Version code must be < $MAX_ALLOWED_LENGTH")
            return pattern
                    .groupingBy { it }
                    .eachCount()
                    .mapTo(mutableSetOf()) { (key, occurrences) -> Component.create(key, occurrences) }
                    .let { CodePattern(it) }
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CodePattern

        if (components != other.components) return false

        return true
    }

    override fun hashCode(): Int {
        return components.hashCode()
    }

    data class Component(val multiplier: Int, val type: Type) {
        companion object {
            fun create(key: Char, occurrences: Int): Component {
                val type = Type.values().find { it.key == key }
                        ?: throw FormatException("Unknown char $key in codeFormat")
                return Component(occurrences, type)
            }
        }

        enum class Type(val key: Char) {
            MAJOR('M'),
            MINOR('N'),
            PATCH('P'),
            BUILD('B'),
            ABI('A'),
            SPACE('X'),
            REVISION('R')
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Component

            if (multiplier != other.multiplier) return false
            if (type != other.type) return false

            return true
        }

        override fun hashCode(): Int {
            var result = multiplier
            result = 31 * result + type.hashCode()
            return result
        }
    }
}
