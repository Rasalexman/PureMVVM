import appdependencies.Builds.APP_ID
import appdependencies.Builds.BUILD_TOOLS
import appdependencies.Builds.COMPILE_VERSION
import appdependencies.Builds.MIN_VERSION
import appdependencies.Builds.TARGET_VERSION
import appdependencies.Libs
import appdependencies.Versions
import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import resources.Resources.App.dirs
import resources.Resources.App.javaDirs

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdkVersion(COMPILE_VERSION)
    buildToolsVersion = BUILD_TOOLS
    defaultConfig {
        applicationId = APP_ID
        minSdkVersion(MIN_VERSION)
        targetSdkVersion(TARGET_VERSION)
        versionCode = appdependencies.Builds.App.VERSION_CODE
        versionName = appdependencies.Builds.App.VERSION_NAME
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        javaCompileOptions.annotationProcessorOptions {
            includeCompileClasspath = true
        }
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

    flavorDimensions("version")
    productFlavors {
        create("dev") {
            applicationId = APP_ID
            versionCode = appdependencies.Builds.App.VERSION_CODE
            versionName = appdependencies.Builds.App.VERSION_NAME
            setDimension("version")
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            multiDexEnabled = true
        }

        create("prod") {
            applicationId = APP_ID
            versionCode = appdependencies.Builds.App.VERSION_CODE
            versionName = appdependencies.Builds.App.VERSION_NAME
            setDimension("version")
            multiDexEnabled = true
        }
    }

    sourceSets {
        getByName("main") {
            java.setSrcDirs(javaDirs)
            res.setSrcDirs(dirs)
        }
        /*all {
            java.setSrcDirs(javaDirs)
            res.setSrcDirs(dirs)
        }*/
        /* As example for specific flavour
        getByName("dev") {
            res.setSrcDirs(dirs)
        }
        */
    }

    dexOptions {
        javaMaxHeapSize = "4g"
    }

    applicationVariants.forEach { variant ->
        variant.outputs.forEach { output ->
            val outputImpl = output as BaseVariantOutputImpl
            val project = project.name
            val sep = "_"
            val flavor = variant.flavorName
            val buildType = variant.buildType.name
            val version = variant.versionName

            val newApkName = "$project$sep$flavor$sep$buildType$sep$version.apk"
            outputImpl.outputFileName = newApkName
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    bundle {
        // language { enableSplit = true }
        // density { enableSplit = true }
        abi {
            enableSplit = false
        }
    }

    /*
    splits {

    }
     */

    tasks.withType<KotlinCompile>().all {
        kotlinOptions.suppressWarnings = true
        kotlinOptions.jvmTarget = "1.8"
        kotlinOptions.noReflect = true
        kotlinOptions.freeCompilerArgs += listOf(
                "-XXLanguage:+InlineClasses",
                "-XXLanguage:+ExperimentalUnsignedTypes"
        )
    }

    packagingOptions {
        exclude("META-INF/notice.txt")
    }

    // Declare the task that will monitor all configurations.
    configurations.all {
        // 2 Define the resolution strategy in case of conflicts.
        resolutionStrategy {
            // Fail eagerly on version conflict (includes transitive dependencies),
            // e.g., multiple different versions of the same dependency (group and name are equal).
            failOnVersionConflict()

            // Prefer modules that are part of this build (multi-project or composite build) over external modules.
            preferProjectModules()
        }
    }

    androidExtensions {
        isExperimental = true
        defaultCacheImplementation = org.jetbrains.kotlin.gradle.internal.CacheImplementation.HASH_MAP
    }
}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(kotlin("stdlib-jdk8", Versions.kotlin))
    implementation(project(":core"))
    implementation(project(":providers"))
    implementation(project(":onboarding"))
    implementation(project(":tabhome"))

    implementation(Libs.Common.viewPagerIndicator)

    testImplementation(Libs.Tests.junit)
    androidTestImplementation(Libs.Tests.runner)
    androidTestImplementation(Libs.Tests.espresso)
}
