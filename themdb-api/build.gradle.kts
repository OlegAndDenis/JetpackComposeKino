plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
//    id("dagger.hilt.android.plugin")
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
    implementation(Libs.gson.gson)
    implementation(Libs.retrofit.converterGson)
    implementation(Libs.retrofit.retrofit)
    implementation(Libs.timber.timber)
    implementation(Libs.coroutines.coroutines)
    implementation(Libs.coroutines.coroutinesAndroid)
    implementation(Libs.okhttp.okhttp)
    kapt(Libs.hilt.compilerKapt)
}