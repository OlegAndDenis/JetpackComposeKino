plugins {
    id("com.android.library")
    id("kotlin-android")
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

    buildFeatures {
        compose = true
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
    implementation(project(":ui-common-resources"))

    implementation(Libs.compose.uiUi)
    implementation(Libs.compose.uiTooling)
    implementation(Libs.compose.composeFoundationFoundation)
    implementation(Libs.compose.composeFoundationLayout)
    implementation(Libs.compose.uiViewBinding)
    implementation(Libs.compose.composeMaterialIconsext)
    implementation(Libs.compose.composeMaterialMaterial)
    implementation(Libs.lifecycle.lifecycleRuntime)
    implementation(Libs.coroutines.coroutinesAndroid)
    implementation(Libs.coroutines.coroutines)
    implementation(Libs.accompanist.pager)
    implementation(Libs.compose.uiUti)
}