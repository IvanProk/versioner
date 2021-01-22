package com.ivanprok.versioner

import org.junit.jupiter.api.Test

class VersionerPluginTest: VersionerTest() {
    @Test
    fun `Using plugin with no repo perform empty output`() {
        assert(plugin.versionName == VERSION_NAME_UNKNOWN)
        assert(plugin.versionCode == VERSION_CODE_UNKNOWN)
    }

    @Test
    fun `Using plugin with empty git tree perform empty output`() {
        assert(plugin.versionName == VERSION_NAME_UNKNOWN)
        assert(plugin.versionCode == VERSION_CODE_UNKNOWN)
    }

}
