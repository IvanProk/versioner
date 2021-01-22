plugins {
    id("java-gradle-plugin")
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "com.ivanprok"
version = "0.1.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.eclipse.jgit", "org.eclipse.jgit",  "5.8.1.202007141445-r")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1:")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation(kotlin("test"))
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.test {
    useJUnitPlatform()
}

gradlePlugin {
    plugins {
        create("versioner") {
            id = "com.ivanprok.versioner"
            implementationClass = "com.ivanprok.versioner.VersionerPlugin"
        }
    }
}
