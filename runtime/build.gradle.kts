plugins {
    kotlin("jvm") version "2.2.0"
}

group = "com.github.callmephil"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Copy>("copyPrimitiveTestDll") {
    println("Copying dlls...")

    from("src/test/testlib/build/Debug")
    include("*.dll")
    into(layout.buildDirectory.dir("libs"))
}

tasks.named<Test>("test") {
    dependsOn("copyPrimitiveTestDll")
}

kotlin {
    jvmToolchain(22)
}