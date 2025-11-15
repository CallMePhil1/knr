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
    version = "0.1.2-alpha"
}