plugins {
    id("com.android.library")
    id("kotlin-android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
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
    buildFeatures {
        compose = true
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

    implementation(Libs.hilt.hilt)
    implementation(Libs.hilt.dagger)
    implementation(Libs.hilt.hiltAndroid)
    implementation(Libs.timber.timber)
    implementation(Libs.compose.uiTooling)
    implementation(Libs.compose.uiUti)
    implementation(Libs.lifecycle.lifecycleViewModel)
    implementation(Libs.lifecycle.viewModelKtx)
    implementation(Libs.coroutines.coroutines)
    implementation(Libs.coroutines.coroutinesAndroid)
    kapt(Libs.hilt.compilerKapt)
    kapt(Libs.hilt.hiltAndroidCompiler)
}