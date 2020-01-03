import appdependencies.Apps.APP_ID
import appdependencies.Apps.BUILD_TOOLS
import appdependencies.Apps.COMPILE_VERSION
import appdependencies.Apps.MIN_VERSION
import appdependencies.Apps.TARGET_VERSION
import appdependencies.Apps.VERSION_CODE
import appdependencies.Apps.VERSION_NAME
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
        versionCode = VERSION_CODE
        versionName = VERSION_NAME
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "SERVER_URL", "\"http://connect.quasa.io/api/v1/\"")

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
            versionCode = VERSION_CODE
            versionName = VERSION_NAME
            setDimension("version")
            applicationIdSuffix = ".dev"
            versionNameSuffix = "-dev"
            multiDexEnabled = true
        }

        create("prod") {
            applicationId = APP_ID
            versionCode = VERSION_CODE
            versionName = VERSION_NAME
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
    // implementation(Libs.Core.material)

    implementation(Libs.Lifecycle.extensions)
    implementation(Libs.Lifecycle.common)

    implementation(Libs.Room.runtime)
    implementation(Libs.Room.ktx)

    implementation(Libs.Retrofit.core)
    implementation(Libs.Retrofit.moshi)
    // implementation(Libs.Retrofit.moshicodegen)
    implementation(Libs.Retrofit.logging)

    implementation(Libs.ImageLoading.coil)

    implementation(Libs.Common.kotpref)

    implementation(Libs.Common.viewPagerIndicator)

    testImplementation(Libs.Tests.junit)
    androidTestImplementation(Libs.Tests.runner)
    androidTestImplementation(Libs.Tests.espresso)

    // kapt(Libs.Retrofit.moshicodegen)
    kapt(Libs.Room.kapt)
}
