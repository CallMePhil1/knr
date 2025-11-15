import org.gradle.kotlin.dsl.dependencies

plugins {
    kotlin("jvm")
    `maven-publish`
}

kotlin {
    jvmToolchain(22)
}

dependencies {
    api(project(":annotations"))
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

tasks.test {
    useJUnitPlatform()
}
