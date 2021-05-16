package com.example.kino.screen

import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieResult

data class GenresList(
    val type: Int = 0,
    val name: String = "",
    val idGenres: Int = 0,
    val listMovie: List<MovieResult> = emptyList()
): NetworkItem
