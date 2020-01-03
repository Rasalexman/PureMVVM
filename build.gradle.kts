import appdependencies.ClassPath
import appdependencies.Versions

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://plugins.gradle.org/m2/") }

    }
    dependencies {
        classpath(appdependencies.ClassPath.gradle)
        classpath(appdependencies.ClassPath.kotlingradle)

        classpath(appdependencies.ClassPath.google)
        classpath(appdependencies.ClassPath.navisafe)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven { url = uri("https://www.jitpack.io") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
