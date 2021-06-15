package com.example.ui_movie.movie

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

//Fixme вынести в отдельный модуль
@Composable
fun TabHost() {
    val allScreens = TabScreen.values().toList()
    var currentScreen by rememberSaveable { mutableStateOf(TabScreen.MovieScreen) }
    Scaffold(
        topBar = {
            TabTopBar(
                tabHostScreens = allScreens,
                currentScreen = currentScreen,
                onTabSelected = { currentScreen = it }
            )
        }
    ) { innerPadding ->
        Box(Modifier.padding(innerPadding)) {
            currentScreen.content(onScreenChange = { screen -> currentScreen = screen })
        }
    }
}
