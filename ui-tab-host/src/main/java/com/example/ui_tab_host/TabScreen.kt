package com.example.ui_tab_host

import androidx.compose.runtime.Composable
import com.example.ui_movie.movie.Movie

enum class TabScreen(
    val title: String,
    private val body: @Composable ((TabScreen) -> Unit) -> Unit
) {
    MovieScreen(
        title = "Movie",
        body = { Movie() }
    ),
    TvScreen(
        title = "Tv",
        body = { Movie() }
    );

    @Composable
    fun content(onScreenChange: (TabScreen) -> Unit) {
        body(onScreenChange)
    }

}