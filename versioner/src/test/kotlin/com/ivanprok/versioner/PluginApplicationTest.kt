package com.ivanprok.versioner

import org.gradle.testfixtures.ProjectBuilder
import org.junit.jupiter.api.Test

class PluginApplicationTest : VersionerTest() {
    @Test
    fun `Using the Plugin ID applies the plugin`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply("com.ivanprok.versioner")
        project.plugins.getPlugin(VersionerPlugin::class.java)
    }
}
