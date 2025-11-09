plugins {
    kotlin("jvm") apply false
}

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    group = "io.github.callmephil.knr"
    version = "0.1"
}