import ImplementsDependency.androidTestImplementation
import ImplementsDependency.apiList
import ImplementsDependency.debuggerDependency
import ImplementsDependency.kaptDependency
import ImplementsDependency.listImplements
import ImplementsDependency.testImplementations
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

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
    signingConfigs {
        create("release") {
            val keystorePatch = gradleLocalProperties(rootDir).getProperty("keystorePatch")
            val password = gradleLocalProperties(rootDir).getProperty("password")
            storeFile = file(keystorePatch)
            storePassword = password
            keyAlias = "scinario"
            keyPassword = password
        }
    }


    buildFeatures {
        viewBinding = true
        compose = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("release")
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