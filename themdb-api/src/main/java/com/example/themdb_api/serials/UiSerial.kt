package com.example.themdb_api.serials

import com.example.themdb_api.movie.MovieResult

data class UiSerial(
    val name: String = "",
    val movies: List<MovieResult> = emptyList()
)
