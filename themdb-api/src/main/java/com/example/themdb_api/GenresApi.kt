package com.example.themdb_api

import kotlinx.serialization.Serializable

@Serializable
data class GenresApi(
    var genres: List<Genres>
)
