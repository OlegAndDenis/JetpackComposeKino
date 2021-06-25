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
        viewBinding = true
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
    implementation(Libs.compose.uiUi)
    implementation(Libs.compose.uiTooling)
    implementation(Libs.compose.composeFoundationFoundation)
    implementation(Libs.compose.composeFoundationLayout)
    implementation(Libs.compose.uiViewBinding)
    implementation(Libs.compose.composeMaterialIconsext)
    implementation(Libs.compose.composeMaterialMaterial)
    implementation(Libs.accompanist.insets)
    implementation(Libs.accompanist.insetsUi)
    implementation(Libs.compose.uiUti)
    implementation(Libs.coil.coil)
    implementation(Libs.accompanist.coil)
    implementation(Libs.accompanist.pager)
    implementation(Libs.timber.timber)
    implementation(Libs.hilt.hiltNavigation)
    implementation(Libs.hilt.hilt)
    implementation(Libs.hilt.dagger)
    implementation(Libs.hilt.hiltAndroid)
    kapt(Libs.hilt.compilerKapt)
    kapt(Libs.hilt.hiltAndroidCompiler)
    implementation(project(Modules.uiCommonCompose))
    implementation(project(Modules.base))
    implementation(project(Modules.themdb))
    implementation(project(Modules.coil))
}