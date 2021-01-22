package com.ivanprok.versioner.formatting

import org.gradle.api.GradleException

class FormatException(override val message: String): GradleException(message)
