package com.example.themdb_api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("tv")
data class SerialResult(
    @SerialName("poster_path")
    val posterPath: String = "no",
    @SerialName("popularity")
    val popularity: Float = 0.0F,
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("backdrop_path")
    val backdropPath: String = "no",
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("overview")
    val overview: String = "no",
    @SerialName("first_air_date")
    val firstAirDate: String = "",
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("genre_ids")
    val genreIds: List<Long> = listOf(),
    @SerialName("original_name")
    val originalName: String = "no",
    @SerialName("original_language")
    val originalLanguage: String = "no",
    @SerialName("release_date")
    val releaseDate: String = "no",
    @SerialName("name")
    val name: String = "no",
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("vote_count")
    val voteCount: Long = 0L,
    @SerialName("adult")
    val adult: Boolean = false,
    override val mediaType: String = "tv",
) : Type()