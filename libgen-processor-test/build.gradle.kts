plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "2.2.20-2.0.4"
}

group = "com.github.callmephil.knr.libgen"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    ksp(project(":libgen-processor"))

    implementation(project(":libgen-annotations"))
    implementation(project(":runtime"))
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")
    implementation("org.tinylog:slf4j-tinylog:2.7.0")
    implementation("org.tinylog:tinylog-impl:2.7.0")

    testImplementation(kotlin("test"))
}

ksp {
    arg("knr.libgen.logLevel", "debug")
}

tasks.test {
    useJUnitPlatform()
}
