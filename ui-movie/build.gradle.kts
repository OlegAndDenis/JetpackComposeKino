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
    implementation(Libs.coil.coil)
    implementation(Libs.accompanist.coil)
    implementation(project(Modules.uiCommonCompose))
}