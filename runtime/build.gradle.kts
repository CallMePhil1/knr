import org.gradle.kotlin.dsl.dependencies

plugins {
    kotlin("jvm")
    `maven-publish`
}

kotlin {
    jvmToolchain(22)
}

dependencies {
    api(projects.annotations)
    implementation(libs.kotlin.logging)
    testImplementation(kotlin("test"))
    testImplementation(libs.kotlinx.coroutines.core)
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

    jvmArgs = listOf("--enable-native-access=ALL-UNNAMED")
}
