plugins {
    kotlin("jvm")
    alias(libs.plugins.ksp)
    application
}

group = "com.github.callmephil.knr"
version = "unspecified"

kotlin {
    jvmToolchain(22)
}

dependencies {
    ksp(projects.processors)

    implementation(projects.annotations)
    implementation(projects.runtime)
    implementation(libs.kotlin.logging)
    implementation(libs.tinylog.impl)
    implementation(libs.tinylog.slf4j)
}

ksp {
    arg("knr.processors.logLevel", "debug")
}

application {
    mainClass = "knr.libgen.processor.LibraryProcessorKt"
}

tasks.withType<PublishToMavenLocal>().configureEach {
    enabled = false
}
tasks.withType<PublishToMavenRepository>().configureEach {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}
