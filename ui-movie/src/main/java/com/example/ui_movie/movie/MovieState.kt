package com.example.ui_movie.movie

import com.example.themdb_api.UiMovie

sealed class MovieState {
    data class Loading(val isLoading: Boolean = false) : MovieState()
    data class Result(
        val uiMovies: List<UiMovie> = emptyList(),
        val top: UiMovie = UiMovie(),
        val isLoading: Boolean = false
    ) : MovieState()
    object ConnectionLost : MovieState()
    object ConnectionAvailable : MovieState()
}