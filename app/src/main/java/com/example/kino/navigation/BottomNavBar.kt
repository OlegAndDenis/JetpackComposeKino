package com.example.kino.navigation

// https://developer.android.com/jetpack/compose/navigation#bottom-nav

/** Bottom navigation bar items */
private val items = listOf(
    RootScreen.Main,
    RootScreen.Following,
    RootScreen.Search,
    RootScreen.Profile,
)



/** Routes that don't need a bottom navigation bar  */
val blackList = listOf(
    Screen.Test.route
)