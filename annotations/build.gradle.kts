plugins {
    kotlin("jvm")
    `maven-publish`
}

kotlin {
    jvmToolchain(22)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}