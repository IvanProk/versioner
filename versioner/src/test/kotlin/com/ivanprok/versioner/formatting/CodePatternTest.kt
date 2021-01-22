package com.ivanprok.versioner.formatting

import org.gradle.api.GradleException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class CodePatternTest {
    private var compiledPattern = CodePattern(setOf(
        CodePattern.Component(2, CodePattern.Component.Type.MAJOR),
        CodePattern.Component(2, CodePattern.Component.Type.MINOR),
        CodePattern.Component(3, CodePattern.Component.Type.PATCH)
    ))

    @Test
    fun `Valid code pattern compiles`() {
        assertEquals(CodePattern.compile(VALID_PATTERN), compiledPattern)
    }

    @Test
    fun `Code pattern with invalid length won't compile`() {
        assertThrows(FormatException::class.java) {
            CodePattern.compile(INVALID_LENGTH_PATTERN)
        }
    }

    @Test
    fun `Code pattern with invalid symbols won't compile`() {
        assertThrows(FormatException::class.java) {
            CodePattern.compile(INVALID_SYMBOL_PATTERN)
        }
    }

    companion object {
        private const val VALID_PATTERN = "MMNNPPP"
        private const val INVALID_LENGTH_PATTERN = "MMMMNNNNNPPPPP"
        private const val INVALID_SYMBOL_PATTERN = "mMMnMnULL"
    }
}
