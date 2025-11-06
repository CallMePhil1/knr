rootProject.name = "knr"
include("runtime")
include("libgen-annotations")
include("libgen-processor")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm") version "2.2.20"
    }
}

include("libgen-processor-test")