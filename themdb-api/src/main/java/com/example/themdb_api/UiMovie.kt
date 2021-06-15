package com.example.themdb_api

data class UiMovie(
    val title: String = "",
    val movies: List<MovieResult> = emptyList()
)