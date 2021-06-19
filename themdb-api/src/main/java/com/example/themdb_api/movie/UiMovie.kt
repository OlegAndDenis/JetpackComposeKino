package com.example.themdb_api.movie

data class UiMovie(
    val title: String = "",
    val movies: List<MovieResult> = emptyList()
)