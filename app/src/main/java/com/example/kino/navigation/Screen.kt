package com.example.kino.root

import androidx.annotation.DrawableRes
import com.example.kino.R

sealed class RootScreen(val route: String, val label: String, @DrawableRes val iconId: Int) {
    object Main : RootScreen("main_root", "Main", R.drawable.ic_home)
    object Following : RootScreen("following_root", "Following", R.drawable.ic_favorite)
    object Search : RootScreen("search_root", "Search", R.drawable.ic_search)
    object Profile : RootScreen("profile_root", "Profile", R.drawable.ic_profile_user)
}