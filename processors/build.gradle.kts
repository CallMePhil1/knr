plugins {
    kotlin("jvm")
    alias(libs.plugins.ksp)
    `maven-publish`
}

kotlin {
    jvmToolchain(22)
}

dependencies {
    implementation(projects.annotations)
    implementation(projects.runtime)
    compileOnly(libs.ksp)
    implementation(libs.kotlinpoet)
    implementation(libs.kotlinpoet.ksp)
    implementation(libs.kotlin.logging)
    implementation(libs.tinylog.slf4j)
    implementation(libs.tinylog.impl)
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
