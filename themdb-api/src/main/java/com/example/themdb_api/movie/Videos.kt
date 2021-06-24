package com.example.themdb_api.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Videos(
    @SerialName("results")
    val videos: List<Video> = emptyList()
)
