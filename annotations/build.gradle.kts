plugins {
    kotlin("jvm")
    `maven-publish`
}

kotlin {
    jvmToolchain(22)
}

repositories {
    mavenCentral()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}