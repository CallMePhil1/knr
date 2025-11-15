plugins {
    kotlin("jvm")
    id("com.google.devtools.ksp") version "2.2.20-2.0.4"
    `maven-publish`
}

kotlin {
    jvmToolchain(22)
}

repositories {
    mavenCentral()
}

val kspVersion: String by project
val kotlinPoet: String by project

dependencies {
    implementation(project(":annotations"))
    implementation(project(":runtime"))
    compileOnly("com.google.devtools.ksp:symbol-processing-api:$kspVersion")
    implementation("com.squareup:kotlinpoet:$kotlinPoet")
    implementation("com.squareup:kotlinpoet-ksp:$kotlinPoet")
    implementation("io.github.oshai:kotlin-logging-jvm:7.0.3")
    implementation("org.tinylog:slf4j-tinylog:2.7.0")
    implementation("org.tinylog:tinylog-impl:2.7.0")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
