import ImplementsDependency.androidTestImplementation
import ImplementsDependency.apiList
import ImplementsDependency.debuggerDependency
import ImplementsDependency.kaptDependency
import ImplementsDependency.listImplements
import ImplementsDependency.testImplementations

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

kapt {
    correctErrorTypes = true
    useBuildCache = true
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = AppVersion.compileSdkVersion
    buildToolsVersion = AppVersion.buildToolsVersion

    defaultConfig {
        applicationId = AppVersion.applicationId
        minSdk = AppVersion.minSdkVersion
        targetSdk = AppVersion.targetSdkVersion
        versionCode = AppVersion.versionCode
        versionName = AppVersion.versionName

        testInstrumentationRunner = AppVersion.stringValue.androidJUnitRunner
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
        debug {
            isDebuggable = true
        }
    }

    sourceSets {
        get("main").java.srcDirs("src/main/kotlin")
        get("androidTest").java.srcDirs("src/androidTest/kotlin")
        get("test").java.srcDirs("src/test/kotlin")
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppVersion.compose
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = AppVersion.targetJvm
    }
}

dependencies {
    apiList.forEach {
        api(it)
    }
    listImplements.forEach {
        implementation(it)
    }
    debuggerDependency.forEach {
        debugImplementation(it)
    }
    kaptDependency.forEach {
        kapt(it)
    }
    testImplementations.forEach {
        testImplementation(it)
    }
    androidTestImplementation.forEach {
        androidTestImplementation(it)
    }
    Modules.modules.forEach {
        implementation(project(it))
    }
}