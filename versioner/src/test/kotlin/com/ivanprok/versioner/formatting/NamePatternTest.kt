package com.ivanprok.versioner.formatting

import org.gradle.api.GradleException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class NamePatternTest {
    private var compiledPattern = NamePattern(setOf(
        NamePattern.Component("tag", NamePattern.Component.Type.TAG),
        NamePattern.Component(".count", NamePattern.Component.Type.COUNT),
        NamePattern.Component("-branch_name", NamePattern.Component.Type.BRANCH_NAME)
    ))

    @Test
    fun `Valid name pattern compiles`() {
        assertEquals(NamePattern.compile(VALID_PATTERN), compiledPattern)
    }

    @Test
    fun `Name pattern with invalid symbols won't compile`() {
        assertThrows(FormatException::class.java) {
            NamePattern.compile(INVALID_SYMBOL_PATTERN)
        }
    }

    companion object {
        private const val VALID_PATTERN = "%tag%%.count%%-branch_name%"
        private const val INVALID_SYMBOL_PATTERN = "%tag%%.version%"
    }
}
