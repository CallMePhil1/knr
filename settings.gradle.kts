rootProject.name = "knr"
include("runtime")
include("annotations")
include("processors")
include("processors-test")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("libs") {
            val kotlinPoetVersion = "2.2.0"
            val kspVersion = "2.2.20-2.0.4"
            val tinylogVersion = "2.7.0"

            library("kotlin-logging","io.github.oshai", "kotlin-logging-jvm").version("7.0.3")
            library("kotlinpoet", "com.squareup", "kotlinpoet").version(kotlinPoetVersion)
            library("kotlinpoet-ksp", "com.squareup", "kotlinpoet-ksp").version(kotlinPoetVersion)
            library("kotlinx-coroutines-core", "org.jetbrains.kotlinx", "kotlinx-coroutines-core").version("1.10.2")
            library("ksp", "com.google.devtools.ksp", "symbol-processing-api").version(kspVersion)
            library("tinylog-impl", "org.tinylog", "tinylog-impl").version(tinylogVersion)
            library("tinylog-slf4j", "org.tinylog", "slf4j-tinylog").version(tinylogVersion)

            plugin("ksp", "com.google.devtools.ksp").version(kspVersion)
        }
    }
}

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    plugins {
        kotlin("jvm") version "2.2.20"
    }
}
