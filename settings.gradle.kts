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
    ":ui-detail",
    ":shared-module",
)
rootProject.buildFileName = "build.gradle.kts"

fun configureGradleScriptKotlinOn(project: ProjectDescriptor) {
    project.buildFileName = "build.gradle.kts"
    project.children.forEach { configureGradleScriptKotlinOn(it) }
}

configureGradleScriptKotlinOn(rootProject)