import resources.Resources.Providers.dirs
import resources.Resources.Providers.javaDirs

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

android {
    compileSdkVersion(appdependencies.Builds.COMPILE_VERSION)
    buildToolsVersion = appdependencies.Builds.BUILD_TOOLS
    defaultConfig {
        minSdkVersion(appdependencies.Builds.MIN_VERSION)
        targetSdkVersion(appdependencies.Builds.TARGET_VERSION)
        versionCode = appdependencies.Builds.Providers.VERSION_CODE
        versionName = appdependencies.Builds.Providers.VERSION_NAME
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles = "consumer-rules.pro"

        buildConfigField("String", "SERVER_URL", "\"https://api.themoviedb.org/3/\"")
        buildConfigField("String", "IMAGES_URL", "\"https://image.tmdb.org/t/p/w500\"")

        buildConfigField("String", "ApiKey", "\"026a257e7842ac9cac1fa627496b1468\"")
        buildConfigField("String", "IMAGES_BACKDROP_URL", "\"https://image.tmdb.org/t/p/original\"")
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            isDebuggable = true
        }

        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    sourceSets {
        getByName("main") {
            java.setSrcDirs(javaDirs)
            res.setSrcDirs(dirs)
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions.suppressWarnings = true
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.noReflect = true
        kotlinOptions.freeCompilerArgs += listOf(
            "-XXLanguage:+InlineClasses",
            "-XXLanguage:+ExperimentalUnsignedTypes"
        )
    }

}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(kotlin("stdlib-jdk8", appdependencies.Versions.kotlin))

    api(project(":core"))

    api(appdependencies.Libs.Room.runtime)
    api(appdependencies.Libs.Room.ktx)

    api(appdependencies.Libs.Retrofit.core)
    api(appdependencies.Libs.Retrofit.moshi)
    api(appdependencies.Libs.Retrofit.logging)

    testImplementation(appdependencies.Libs.Tests.junit)
    androidTestImplementation(appdependencies.Libs.Tests.runner)
    androidTestImplementation(appdependencies.Libs.Tests.espresso)

    kapt(appdependencies.Libs.Room.kapt)
}
