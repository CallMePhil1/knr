import org.gradle.kotlin.dsl.dependencies

plugins {
    kotlin("jvm")
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

tasks.test {
    useJUnitPlatform()
}
