package com.example.themdb_api.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Images(
    @SerialName("backdrops")
    val backdrops: List<Backdrops> = emptyList(),
    @SerialName("posters")
    val posters: List<Posters> = emptyList(),
)
