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

    kspTest(projects.processors)
    testImplementation(kotlin("test"))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

tasks.test {
    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}
