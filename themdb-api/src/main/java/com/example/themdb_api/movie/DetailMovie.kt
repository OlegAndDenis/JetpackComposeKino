package com.example.themdb_api.movie

import com.example.themdb_api.genres.Genres
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetailMovie(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("backdrop_path")
    val backdropPath: String = "",
    @SerialName("budget")
    val budget: Long = 0,
    @SerialName("genres")
    val genres: List<Genres> = emptyList(),
    @SerialName("homepage")
    val homepage: String = "",
    @SerialName("id")
    val id: Long = 0,
    @SerialName("imdb_id")
    val imdbId: String = "",
    @SerialName("original_language")
    val originalLanguage: String = "",
    @SerialName("original_title")
    val originalTitle: String = "",
    @SerialName("title")
    val title: String = "",
    @SerialName("overview")
    val overview: String = "",
    @SerialName("popularity")
    val popularity: Float = 0.0F,
    @SerialName("poster_path")
    val posterPath: String = "",
    @SerialName("release_date")
    val releaseDate: String = "",
    @SerialName("revenue")
    val revenue: Long = 0L,
    @SerialName("runtime")
    val runtime: Int = 0,
    @SerialName("status")
    val status: String = "",
    @SerialName("tagline")
    val tagline: String = "",
    @SerialName("video")
    val video: Boolean = false,
    @SerialName("vote_average")
    val voteAverage: Double = 0.0,
    @SerialName("vote_count")
    val voteCount: Double = 0.0,
    @SerialName("videos")
    val videos: Videos = Videos(),
    @SerialName("images")
    val images: Images = Images(),
)
