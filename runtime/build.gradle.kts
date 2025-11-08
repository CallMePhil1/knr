plugins {
    kotlin("jvm")
}

group = "com.github.callmephil"
version = "0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":libgen-annotations"))
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

tasks.test {
    useJUnitPlatform()
}
