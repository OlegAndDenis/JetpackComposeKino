package com.example.kino.screen.movie

import com.example.kino.network.model.movie.MovieResult

data class OldAndNewList(
    val old: List<MovieResult> = emptyList(),
    val new: List<MovieResult> = emptyList(),
)