package com.example.themdb_api.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieApi(
    @SerialName("page")
    val page: Int = 0,
    @SerialName("results")
    val result: List<MovieResult>,
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0,
)
