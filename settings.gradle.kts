pluginManagement {
    repositories {
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "kotlin-multiplatform" -> useModule("org.jetbrains.kotlin:kotlin-gradle-plugin:${requested.version}")
                "kotlinx-serialization" -> "org.jetbrains.kotlin:kotlin-serialization:${requested.version}"
            }
        }
    }
}

include(
    ":app",
    ":ui-common-compose",
    ":ui-movie",
    ":ui-profile",
    ":themdb-api",
    ":ui-common-resources",
    ":base",
    ":ui-login",
    ":ui-tab-host",
    ":coil-image",
    ":ui-tv",
    ":shared-module",
    ":backend-module",
)
rootProject.buildFileName = "build.gradle.kts"

fun configureGradleScriptKotlinOn(project: ProjectDescriptor) {
    project.buildFileName = "build.gradle.kts"
    project.children.forEach { configureGradleScriptKotlinOn(it) }
}

configureGradleScriptKotlinOn(rootProject)