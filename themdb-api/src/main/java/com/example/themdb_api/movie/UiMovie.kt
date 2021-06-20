package com.example.themdb_api.movie

data class UiMovie(
    val title: String = "",
    val genreId: Int = -99,
    val movies: List<MovieResult> = emptyList()
)