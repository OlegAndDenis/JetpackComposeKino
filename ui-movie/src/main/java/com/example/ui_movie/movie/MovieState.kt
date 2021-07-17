package com.example.ui_movie.movie

import com.example.base.network.base.ViewState
import com.example.themdb_api.movie.UiMovie

sealed class MovieState : ViewState {
    object Init: MovieState()
    object Loading : MovieState()
    data class Result(
        val uiMovies: List<UiMovie> = emptyList(),
        val isLoading: Boolean = false
    ) : MovieState()
    object ConnectionLost : MovieState()
    object ConnectionAvailable : MovieState()
}