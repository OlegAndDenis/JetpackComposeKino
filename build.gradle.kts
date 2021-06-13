// Top-level build file where you can add configuration options common to all sub-projects/modules.
@Suppress("AndroidGradlePluginVersion")
buildscript {
    val kotlinVersion by extra(AppVersion.kotlin)
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(GradlePlugins.androidBuildTools)
        classpath(GradlePlugins.kotlin)
        classpath(GradlePlugins.daggerHilt)
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("com.android.tools.build:gradle:7.0.0-beta03")
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}