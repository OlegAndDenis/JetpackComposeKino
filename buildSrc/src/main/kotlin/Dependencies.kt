import Libs.accompanist
import Libs.androidX
import Libs.coil
import Libs.compose
import Libs.coroutines
import Libs.gson
import Libs.hilt
import Libs.kotlin
import Libs.leakcanary
import Libs.lifecycle
import Libs.navigation
import Libs.okhttp
import Libs.paging
import Libs.retrofit
import Libs.room
import Libs.test
import Libs.timber
import Libs.ui

object ImplementsDependency {
    val listImplements = listOf(
        kotlin,
        ui.adapterDelegateKotlinDsl,
        ui.adapterDelegateKotlinDslLayoutContainer,
        ui.materialComponents,
        room.roomKtx,
        room.roomRuntime,
        gson.gson,
        androidX.appCompat,
        androidX.constraintLayout,
        androidX.coreKtx,
        androidX.fragment,
        retrofit.converterGson,
        retrofit.retrofit,
        hilt.dagger,
        hilt.hiltAndroid,
        timber.timber,
        okhttp.okhttp,
        coroutines.coroutines,
        coroutines.coroutinesAndroid,
        compose.composeAnimationAnimation,
        compose.composeFoundationFoundation,
        compose.composeFoundationLayout,
        compose.composeMaterialIconsext,
        compose.composeMaterialMaterial,
        compose.uiTooling,
        compose.uiUi,
        compose.uiUti,
        compose.uiViewBinding,
        hilt.hintWork,
        lifecycle.lifecycleViewModel,
        lifecycle.uiUi,
        lifecycle.viewModelKtx,
        navigation.navigationComponentCompose,
        room.roomCommon,
        androidX.androidxActivityCompose,
        paging.pagingCompos,
        paging.pagingCommon,
        accompanist.coil,
        accompanist.insets,
        accompanist.swipeRefresh,
        accompanist.flowLayout,
    )

    val apiList = listOf(
        lifecycle.lifecycleRuntime,
        paging.pagingRuntime,
        coil.coil,
        coil.coilGif,
    )

    val kaptDependency = listOf(
        hilt.compilerKapt,
        hilt.hiltAndroidCompiler,
        room.kaptCompiller
    )

    val debuggerDependency = listOf(
        leakcanary.leakcanaryDebug
    )

    val testImplementations = listOf(
        test.junit,
        hilt.hiltTesting
    )

    val androidTestImplementation = listOf(
        test.espresso,
        test.junitExt,
        compose.composeUiTest,
    )
}

private object Libs {

    const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${AppVersion.kotlin}"

    val ui = UI
    val androidX = AndroidX
    val test = Test
    val room = Room
    val gson = Gson
    val retrofit = Retrofit
    val hilt = Hilt
    val leakcanary = Leakcanary
    val coroutines = Coroutines
    val timber = Timber
    val coil = Coil
    val okhttp = Okhttp
    val compose = Compose
    val lifecycle = Lifecycle
    val navigation = NavigationComponent
    val paging = Paging
    val accompanist = Accompanist


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
        private const val androidxActivityComposeVersion = "1.3.0-beta01"

        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
        const val coreKtx = "androidx.core:core-ktx:$coreKtxVersion"
        const val fragment = "androidx.fragment:fragment-ktx:$fragmentKtx"
        const val androidxActivityCompose = "androidx.activity:activity-compose:$androidxActivityComposeVersion"
    }

    object Room {
        private const val roomVersion = "2.3.0"

        const val roomCommon = "androidx.room:room-common:$roomVersion"
        const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
        const val kaptCompiller = "androidx.room:room-compiler:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    }

    object Gson {
        private const val gsonVersion = "2.8.6"
        const val gson = "com.google.code.gson:gson:$gsonVersion"
    }

    object Retrofit {
        private const val retrofitVersion = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val converterGson = "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    }

    object Hilt {
        private const val hiltVersion = "1.0.0"
        private const val hiltNavigationVersion = "1.0.0-alpha02"
        private const val daggerVersion = "2.33"

        const val hilt = "androidx.hilt:hilt-compiler:$hiltVersion"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:$hiltNavigationVersion"
            const val hintWork = "androidx.hilt:hilt-work$hiltNavigation"

        const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:${AppVersion.hilt}"
        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val compilerKapt = "com.google.dagger:dagger-compiler:$daggerVersion"
        const val hiltAndroid = "com.google.dagger:hilt-android:${AppVersion.hilt}"
        const val hiltTesting = "com.google.dagger:hilt-android-testing:${AppVersion.hilt}"
    }

    object Lifecycle {
        private const val uiUiVersion = "1.0.0-beta07"
        private const val lifecycleVersion = "1.3.0"
        private const val viewModelVersion = "1.0.0-alpha05"
        private const val viewModelKtxVersion = "2.4.0-alpha01"

        const val uiUi = "androidx.compose.ui:ui-tooling:$uiUiVersion"
        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val lifecycleViewModel ="androidx.lifecycle:lifecycle-viewmodel-compose:$viewModelVersion"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$viewModelKtxVersion"
    }

    object NavigationComponent {
        private const val nav_version = "2.4.0-alpha02"
        const val navigationComponentCompose =
            "androidx.navigation:navigation-compose:$nav_version"
    }

    object Paging {
        private const val pagingVersion = "3.0.0"
        private const val pagingComposVersion = "1.0.0-alpha10"

        const val pagingCommon = "androidx.paging:paging-common-ktx:$pagingVersion"
        const val pagingRuntime = "androidx.paging:paging-runtime-ktx:$pagingVersion"
        const val pagingCompos = "androidx.paging:paging-compose:$pagingComposVersion"

    }

    object Accompanist {
        private const val accompanistVersion = "0.11.1"
        const val coil = "com.google.accompanist:accompanist-coil:$accompanistVersion"
        const val insets = "com.google.accompanist:accompanist-insets:$accompanistVersion"
        const val flowLayout = "com.google.accompanist:accompanist-flowlayout:$accompanistVersion"
        const val swipeRefresh = "com.google.accompanist:accompanist-swiperefresh:$accompanistVersion"
    }

    object Leakcanary {
        private const val leakcanaryVersion = "2.7"

        const val leakcanaryDebug = "com.squareup.leakcanary:leakcanary-android:$leakcanaryVersion"
    }

    object Coroutines {
        private const val coroutineCoreVersion = "1.5.0"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineCoreVersion"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutineCoreVersion"
    }

    object Timber {
        private const val timberVersion = "4.7.1"

        const val timber = "com.jakewharton.timber:timber:$timberVersion"
    }

    object Coil {
        private const val coilVersion = "1.2.2"

        const val coil = "io.coil-kt:coil:$coilVersion"
        const val coilGif = "io.coil-kt:coil-gif:$coilVersion"
    }

    object Compose {
        const val composeAnimationAnimation = "androidx.compose.animation:animation:${AppVersion.compose}"
        const val composeFoundationFoundation = "androidx.compose.foundation:foundation:${AppVersion.compose}"
        const val composeFoundationLayout = "androidx.compose.foundation:foundation-layout:${AppVersion.compose}"
        const val composeMaterialIconsext = "androidx.compose.material:material-icons-extended:${AppVersion.compose}"
        const val composeMaterialMaterial = "androidx.compose.material:material:${AppVersion.compose}"
        const val composeUiTest = "androidx.compose.ui:ui-test-junit4:${AppVersion.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${AppVersion.compose}"
        const val uiUi = "androidx.compose.ui:ui:${AppVersion.compose}"
        const val uiUti = "androidx.compose.ui:ui-util:${AppVersion.compose}"
        const val uiViewBinding = "androidx.compose.ui:ui-viewbinding:${AppVersion.compose}"
    }

    object Okhttp {
        private const val okhttpVersion = "5.0.0-alpha.2"

        const val okhttp = "com.squareup.okhttp3:logging-interceptor:$okhttpVersion"
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


