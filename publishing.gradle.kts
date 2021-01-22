import org.jfrog.gradle.plugin.artifactory.dsl.ArtifactoryPluginConvention
import org.jfrog.gradle.plugin.artifactory.dsl.DoubleDelegateWrapper
import org.jfrog.gradle.plugin.artifactory.dsl.PublisherConfig
import org.jfrog.gradle.plugin.artifactory.dsl.ResolverConfig

buildscript {
    repositories {
        maven("https://plugins.gradle.org/m2")
        jcenter()
    }

    dependencies {
        classpath("org.jfrog.buildinfo:build-info-extractor-gradle:4.15.2")
    }
}

apply(plugin = "maven-publish")
apply(plugin = "com.jfrog.artifactory")

val archives by configurations

val artifactory_context_url: String by project
val artifactory_rel_repo_key: String by project
val artifactory_user: String by project
val artifactory_password: String by project


configure<PublishingExtension> {
    publications {
        create<MavenPublication>("maven") {
            setArtifacts(archives.artifacts)
        }
    }
}

configure<ArtifactoryPluginConvention> {
    setContextUrl(artifactory_context_url)   //The base Artifactory URL if not overridden by the publisher/resolver
    publish(delegateClosureOf<PublisherConfig> {
        repository(delegateClosureOf<DoubleDelegateWrapper> {
            setProperty("repoKey", artifactory_rel_repo_key)
            setProperty("username", artifactory_user)
            setProperty("password", artifactory_password)
            setProperty("maven", true)
        })

        defaults(delegateClosureOf<groovy.lang.GroovyObject> {
            invokeMethod("publications", arrayOf("release", "debug"))
            // Reference to Gradle publications defined in the build script.
            // This is how we tell the Artifactory Plugin which artifacts should be
            // published to Artifactory.


            setProperty("publishBuildInfo", false)
            setProperty("publishArtifacts", true)
            setProperty("publishPom", true) // Publish generated POM files to Artifactory (true by default)
            setProperty("publishIvy", false)
        })
    })

    resolve(delegateClosureOf<ResolverConfig> {
        repository(delegateClosureOf<DoubleDelegateWrapper> {
            setProperty("repoKey", artifactory_rel_repo_key)
            setProperty("username", artifactory_user)
            setProperty("password", artifactory_password)
            setProperty("maven", true)
        })
    })
}