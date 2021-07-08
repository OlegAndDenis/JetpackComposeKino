package com.example.themdb_api.movie

import com.example.themdb_api.common.MovieResult

data class UiMovie(
    val title: String = "",
    val genreId: Int = -99,
    val movies: List<MovieResult> = emptyList()
)