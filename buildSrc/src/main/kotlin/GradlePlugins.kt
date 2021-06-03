object GradlePlugins {
    private const val androidBuildToolsVersion = "4.2.1"

    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${AppVersion.kotlin}"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"
    const val navigationComponentSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${ImplementsDependency.nav_version}"
}