package com.ivanprok.versioner

import com.ivanprok.versioner.formatting.CodePattern
import com.ivanprok.versioner.formatting.NamePattern
import com.ivanprok.versioner.formatting.VersionFormat

class RuleToFormatMapper {
    fun map(source: Rule): VersionFormat = with(source) {
        VersionFormat(
                namePattern.let { NamePattern.compile(it) },
                codePattern.let { CodePattern.compile(it) },
                baseCode,
                commitHashLength,
                untrackedIsDirty
        )
    }
}
