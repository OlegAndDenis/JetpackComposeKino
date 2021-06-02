import Libs.androidX
import Libs.coroutines
import Libs.glide
import Libs.gson
import Libs.jetpack
import Libs.kotlin
import Libs.leakcanary
import Libs.okhttp
import Libs.retrofit
import Libs.room
import Libs.rxJava
import Libs.test
import Libs.timber
import Libs.ui

object Versions {
    const val compileSdkVersion = 30
    const val targetSdkVersion = 30
    const val minSdkVersion = 23
    const val buildToolsVersion = "30.0.3"
    const val applicationId = "com.example.kino"
    const val versionCode = 1
    const val versionName = "1.0"

    const val kotlin = "1.4.32"
}

object GradlePlugins {
    private const val androidBuildToolsVersion = "4.2.1"

    //
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"
    const val navigationComponentSafeArgs =
        "android.arch.navigation:navigation-safe-args-gradle-plugin:${jetpack.navigationComponentVersion}"
}

object ImplementsDependency {
    val listImplements = listOf(
        kotlin,
        ui.adapterDelegateKotlinDsl,
        ui.adapterDelegateKotlinDslLayoutContainer,
        ui.materialComponents,
        jetpack.navigationComponentFragmentKtx,
        jetpack.navigationComponentUiKtx,
        room.roomKtx,
        room.roomRxJava,
        room.roomRuntime,
        gson.gson,
        glide.glide,
        androidX.appCompat,
        androidX.constraintLayout,
        androidX.coreKtx,
        androidX.fragment,
        retrofit.adapterRxJava2,
        retrofit.converterGson,
        retrofit.rxjava,
        retrofit.retrofit,
        Libs.dagger.dagger,
        timber.timber,
        okhttp.okhttp,
        coroutines.coroutines,
        rxJava.rxAndroid,
        rxJava.rxJava
    )

    val kaptDependency = listOf(
        Libs.dagger.compilerKapt,
        room.kaptCompiller
    )

    val annotationProcessDependency = listOf(
        glide.annotationProcessor
    )

    val compileOnlyDependency = listOf(
        Libs.dagger.jsrAnnotationCompileOnly
    )

    val debuggerDependency = listOf(
        leakcanary.leakcanaryDebugg
    )

    val testImplementations = listOf(
        test.junit
    )

    val androidTestImplementation = listOf(
        test.espresso,
        test.junitExt
    )
}

//Fixme change android.arch.navigation:navigation-ui to androidx.navigation:navigation

//dependencies {
//    val nav_version = "2.3.5"
//
//    // Java language implementation
//    implementation("androidx.navigation:navigation-fragment:$nav_version")
//    implementation("androidx.navigation:navigation-ui:$nav_version")
//
//    // Kotlin
//    implementation("androidx.navigation:navigation-fragment-ktx:$nav_version")
//    implementation("androidx.navigation:navigation-ui-ktx:$nav_version")
//
//    // Feature module Support
//    implementation("androidx.navigation:navigation-dynamic-features-fragment:$nav_version")
//
//    // Testing Navigation
//    androidTestImplementation("androidx.navigation:navigation-testing:$nav_version")
//
//    // Jetpack Compose Integration
//    implementation("androidx.navigation:navigation-compose:2.4.0-alpha02")
//}

private object Libs {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"


    val ui = UI
    val androidX = AndroidX
    val jetpack = Jetpack
    val test = Test
    val room = Room
    val gson = Gson
    val retrofit = Retrofit
    val dagger = Dagger
    val leakcanary = Leakcanary
    val coroutines = Coroutines
    val timber = Timber
    val glide = Glide
    val okhttp = Okhttp
    val rxJava = RxJava


    object UI {
        private const val materialComponentsVersion = "1.3.0"
        private const val adapterDelegateVersion = "4.3.0"

        const val materialComponents =
            "com.google.android.material:material:$materialComponentsVersion"
        const val adapterDelegateKotlinDsl =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl:$adapterDelegateVersion"
        const val adapterDelegateKotlinDslLayoutContainer =
            "com.hannesdorfmann:adapterdelegates4-kotlin-dsl-layoutcontainer:$adapterDelegateVersion"
    }

    object AndroidX {
        private const val constraintLayoutVersion = "2.0.4"
        private const val appCompatVersion = "1.3.0"
        private const val coreKtxVersion = "1.5.0"
        private const val fragmentKtx = "1.3.4"


        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
        const val fragment = "androidx.fragment:fragment-ktx:$fragmentKtx"
    }

    object Room {
        private const val roomVersion = "2.3.0"

        const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
        const val kaptCompiller = "androidx.room:room-compiler:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
        const val roomRxJava = "androidx.room:room-rxjava2:$roomVersion"
    }

    object Gson {
        private const val gsonVersion = "2.8.6"
        const val gson = "com.google.code.gson:gson:$gsonVersion"
    }

    object Retrofit {
        private const val retrofitVersion = "2.9.0"
        private const val rxJava2Adapter = "1.0.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val rxjava = "com.squareup.retrofit2:adapter-rxjava:$retrofitVersion"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
        const val adapterRxJava2 =
            "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:$rxJava2Adapter"
    }

    object Dagger {
        private const val daggerVersion = "2.33"
        private const val jsr = "1.0"

        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val compilerKapt = "com.google.dagger:dagger-compiler:$daggerVersion"
        const val jsrAnnotationCompileOnly = "javax.annotation:jsr250-api:$jsr"
    }

    object Jetpack {
        const val navigationComponentVersion = "1.0.0-alpha05"

        const val navigationComponentFragmentKtx =
            "android.arch.navigation:navigation-fragment:$navigationComponentVersion"
        const val navigationComponentUiKtx =
            "android.arch.navigation:navigation-ui:$navigationComponentVersion"
    }

    object Leakcanary {
        private const val leakcanaryVersion = "2.7"

        const val leakcanaryDebugg = "com.squareup.leakcanary:leakcanary-android:$leakcanaryVersion"
    }

    object Coroutines {
        private const val coroutineCoreVersion = "1.4.2"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineCoreVersion"

    }

    object Timber {
        private const val timberVersion = "4.7.1"

        const val timber = "com.jakewharton.timber:timber:$timberVersion"
    }

    object Glide {
        private const val glideVersion = "4.12.0"

        const val glide = "com.github.bumptech.glide:glide:$glideVersion"
        const val annotationProcessor = "com.github.bumptech.glide:compiler:$glideVersion"
    }

    object Okhttp {
        private const val okhttpVersion = "5.0.0-alpha.2"

        const val okhttp = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
    }

    object RxJava {
        private const val rxJavaVersion = "2.1.1"

        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:$rxJavaVersion"
        const val rxJava = "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
    }

    object Test {
        private const val junitVersion = "4.13.2"
        private const val junitExtVersion = "1.1.2"
        private const val espressoVersion = "3.3.0"

        const val junit = "junit:junit:$junitVersion"
        const val junitExt = "androidx.test.ext:junit:$junitExtVersion"
        const val espresso = "androidx.test.espresso:espresso-core:$espressoVersion"
    }
}


