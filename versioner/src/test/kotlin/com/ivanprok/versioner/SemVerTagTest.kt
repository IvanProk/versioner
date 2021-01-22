package com.ivanprok.versioner

import org.junit.jupiter.api.Test
import kotlin.math.exp
import kotlin.test.assertEquals

class SemVerTagTest {

    @Test
    fun `Classic numeric semver tag parsed successfully`() {
        val given = "1.2.3"
        val parsed = SemVerTag.parse(given)
        val expected = SemVerTag(given, 1, 2, 3)
        assertEquals(parsed, expected)
    }

    @Test
    fun `Numeric semver tag with build number parsed successfully`() {
        val given = "1.2.3.1235235235"
        val parsed = SemVerTag.parse(given)
        val expected = SemVerTag(given, 1, 2, 3)
        assertEquals(parsed, expected)
    }

    @Test
    fun `Numeric semver tag of two digits parsed successfully`() {
        val given = "1.2"
        val parsed = SemVerTag.parse(given)
        val expected = SemVerTag(given, 1, 2, 0)
        assertEquals(parsed, expected)
    }

    @Test
    fun `Numeric semver tag of one digit parsed successfully`() {
        val given = "1"
        val parsed = SemVerTag.parse(given)
        val expected = SemVerTag(given, 1, 0, 0)
        assertEquals(parsed, expected)
    }

    @Test
    fun `Numeric semver tag with postfix parsed successfully`() {
        val given = "1.2.3-alpha"
        val parsed = SemVerTag.parse(given)
        val expected = SemVerTag(given, 1, 2, 3)
        assertEquals(parsed, expected)
    }

    companion object {
        const val SEMVER_TAG_NUMERIC_WITH_BUILD_NUMBER = "1.2.3.2350232352"
        const val SEMVER_TAG_NUMERIC_2 = "1.2"
        const val SEMVER_TAG_NUMERIC_1 = "1"
        const val SEMVER_TAG_NUMERIC_WITH_POSTFIX = "1.2.3-alpha"
    }
}
