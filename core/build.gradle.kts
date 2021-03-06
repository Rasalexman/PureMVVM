import resources.Resources.Core.dirs
import resources.Resources.Core.javaDirs

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
        versionCode = appdependencies.Builds.Core.VERSION_CODE
        versionName = appdependencies.Builds.Core.VERSION_NAME
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
            "-XXLanguage:+InlineClasses"
        )
    }

    dataBinding { isEnabled = true }

    androidExtensions {
        isExperimental = true
        defaultCacheImplementation = org.jetbrains.kotlin.gradle.internal.CacheImplementation.HASH_MAP
    }

}

dependencies {
    implementation(fileTree(mapOf("include" to listOf("*.jar"), "dir" to "libs")))
    implementation(kotlin("stdlib-jdk8", appdependencies.Versions.kotlin))

    api(appdependencies.Libs.Core.appcompat)
    api(appdependencies.Libs.Core.coreKtx)
    api(appdependencies.Libs.Core.constraintlayout)
    api(appdependencies.Libs.Core.navigationFragmentKtx)
    api(appdependencies.Libs.Core.navigationUiKtx)
    api(appdependencies.Libs.Core.viewPager2)
    api(appdependencies.Libs.Core.paging)
    api(appdependencies.Libs.Core.swipeRefreshLayout)
    api(appdependencies.Libs.Core.material)

    api(appdependencies.Libs.Room.runtime)
    api(appdependencies.Libs.Room.ktx)

    api(appdependencies.Libs.Lifecycle.livedataKtx)
    api(appdependencies.Libs.Lifecycle.viewmodelKtx)
    api(appdependencies.Libs.Lifecycle.savedStateViewModel)
    api(appdependencies.Libs.Lifecycle.extensions)
    api(appdependencies.Libs.Lifecycle.common)

    api(appdependencies.Libs.FastAdapter.core)
    api(appdependencies.Libs.FastAdapter.ui)
    api(appdependencies.Libs.FastAdapter.uiExt)
    api(appdependencies.Libs.FastAdapter.diff)
    api(appdependencies.Libs.FastAdapter.paged)

    api(appdependencies.Libs.KotPref.core)
    api(appdependencies.Libs.KotPref.liveData)

    api(appdependencies.Libs.ImageLoading.coil)

    api(appdependencies.Libs.Common.coroutinesmanager)
    api(appdependencies.Libs.Common.circleimageview)
    api(appdependencies.Libs.Common.kodi)
    api(appdependencies.Libs.Common.timber)

    testImplementation(appdependencies.Libs.Tests.junit)
    androidTestImplementation(appdependencies.Libs.Tests.runner)
    androidTestImplementation(appdependencies.Libs.Tests.espresso)
}
