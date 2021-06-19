import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("kotlinx-serialization")
}

repositories {
    google()
    mavenCentral()
}

android {
    compileSdk = AppVersion.compileSdkVersion
    buildToolsVersion = AppVersion.buildToolsVersion

    defaultConfig {
        minSdk = AppVersion.minSdkVersion
        targetSdk = AppVersion.targetSdkVersion

        testInstrumentationRunner = AppVersion.stringValue.androidJUnitRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = true
            val tmdbApiKey = gradleLocalProperties(rootDir).getProperty("tmdbApiKey")
            buildConfigField("String", "TMDB_API_KEY", "\"$tmdbApiKey\"")
        }
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
    implementation(Libs.hilt.hilt)
    implementation(Libs.hilt.dagger)
    implementation(Libs.hilt.hiltAndroid)
    implementation(Libs.retrofit.retrofit)
    implementation(Libs.timber.timber)
    implementation(Libs.coroutines.coroutines)
    implementation(Libs.coroutines.coroutinesAndroid)
    implementation(Libs.okhttp.okhttp)
    implementation(Libs.serialisation.serialisationAdapter)
    implementation(Libs.serialisation.json)
    implementation(project(Modules.base))
    kapt(Libs.hilt.compilerKapt)
    kapt(Libs.hilt.hiltAndroidCompiler)
}