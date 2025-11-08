rootProject.name = "knr"
include("runtime")
include("libgen-annotations")
include("libgen-processor")
include("libgen-processor-test")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm") version "2.2.20"
    }
}
