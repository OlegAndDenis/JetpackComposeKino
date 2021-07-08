// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("AndroidGradlePluginVersion")
buildscript {
    val kotlinVersion by extra(AppVersion.kotlin)
    val androidBuildToolsVersion by extra(AppVersion.androidGradle)

    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
        maven { setUrl("https://dl.bintray.com/jetbrains/kotlin-native-dependencies") }
        maven { setUrl("https://dl.bintray.com/svok/jar2npm") }
        maven { setUrl("https://maven.google.com") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:$androidBuildToolsVersion")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.google.dagger:hilt-android-gradle-plugin:${AppVersion.hilt}")
        classpath("org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
        maven { setUrl("https://dl.bintray.com/jetbrains/kotlin-native-dependencies") }
        maven { setUrl("https://dl.bintray.com/svok/jar2npm") }
        maven { setUrl("https://maven.google.com") }
    }
}

subprojects {
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://kotlin.bintray.com/kotlinx") }
        maven { setUrl("https://maven.google.com") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}