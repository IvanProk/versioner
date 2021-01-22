package com.ivanprok.versioner

import org.gradle.api.Plugin
import org.gradle.api.Project

class VersionerPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            extensions.create(PLUGIN_EXTENSION_NAME, VersionerExtension::class.java, project)
            tasks.apply {
                register("versioner") {
                    it.doLast {
                        println("version name = ${extensions.versioner.versionName}")
                        println("version code = ${extensions.versioner.versionCode}")
                    }
                }

                register("generateVersionName") {
                    it.doLast {
                        println("version name = ${extensions.versioner.versionName}")
                    }
                }

                register("generateVersionCode") {
                    it.doLast {
                        println("version code = ${extensions.versioner.versionCode}")
                    }
                }
            }
        }
    }
}
