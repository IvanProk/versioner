package com.ivanprok.versioner

import org.jetbrains.annotations.Contract

/**
 * Представление git тэга в качестве трезразрядной [semantic version](https://semver.org/).
 *
 * @see SemVerTag.parse
 */
data class SemVerTag(
    val raw: String,
    val major: Int,
    val minor: Int,
    val patch: Int
) : Comparable<SemVerTag> {

    override fun compareTo(other: SemVerTag): Int = compareValuesBy(this, other,
        { it.major },
        { it.minor },
        { it.patch }
    )


    override fun hashCode(): Int {
        var result = major
        result = 31 * result + minor
        result = 31 * result + patch
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SemVerTag

        if (major != other.major) return false
        if (minor != other.minor) return false
        if (patch != other.patch) return false

        return true
    }

    companion object {
        /**
         * Парсит git tag в трехразарядный [SemVerTag].
         *
         * Все разряды после третьего учитываться не будут.
         * Если же git tag имеет меньше трех разрядов, отсутствующие разряды будут заполнены нулями.
         *
         */
        fun parse(tagName: String): SemVerTag {
            val tag = tagName.replace(Regex("[a-zA-Z -]"), "")
            var major = 0
            var minor = 0
            var patch = 0
            tag.split(".").mapIndexed { index, part ->
                when (index) {
                    0 -> major = part.toInt()
                    1 -> minor = part.toInt()
                    2 -> patch = part.toInt()
                }
            }
            return SemVerTag(tagName, major, minor, patch)
        }

        val UNKNOWN = SemVerTag("untagged", 0, 0, 0)
    }
}
