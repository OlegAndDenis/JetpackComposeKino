// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    val kotlin_version by extra("1.4.32")
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(GradlePlugins.androidBuildTools)
        classpath(GradlePlugins.kotlin)
        classpath(GradlePlugins.navigationComponentSafeArgs)
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.android.tools.build:gradle:4.2.1")
    }

    configurations.all {
        resolutionStrategy {
            eachDependency {
                if (requested.group == "org.jetbrains.trove4j" && requested.name == "trove4j" && requested.version == "20160824") {
                    useTarget("org.jetbrains.intellij.deps:trove4j:1.0.20181211")
                }
            }
        }
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