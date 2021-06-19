package com.example.themdb_api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieResult(
    @SerialName("backdrop_path")
    val backdropPath: String = "no",
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("genre_ids")
    val genreIds: List<Long> = listOf(),
    @SerialName("original_title")
    val originalTitle: String = "no",
    @SerialName("original_language")
    val originalLanguage: String = "no",
    @SerialName("overview")
    val overview: String = "no",
    @SerialName("popularity")
    val popularity: Float = 0.0F,
    @SerialName("poster_path")
    val posterPath: String = "no",
    @SerialName("release_date")
    val releaseDate: String = "no",
    @SerialName("title")
    val title: String = "no",
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Long = 0L,
    @SerialName("adult")
    val adult: Boolean = false,
)
