plugins {
    kotlin("jvm") apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "io.github.knr"
    version = "0.1.1-alpha"

    if (this.name != "processors-test") {
        apply {
            plugin("maven-publish")
        }
    }
}