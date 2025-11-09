rootProject.name = "knr"
include("runtime")
include("annotations")
include("processors")
include("processors-test")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm") version "2.2.20"
    }
}
