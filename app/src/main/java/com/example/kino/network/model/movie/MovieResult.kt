package com.example.kino.network.model.movie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieResult(
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String = "no",
    @SerializedName("id")
    @Expose
    val id: Long = 0L,
    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Long> = listOf(),
    @SerializedName("original_title")
    @Expose
    val originalTitle: String = "no",
    @SerializedName("overview")
    @Expose
    val overview: String = "no",
    @SerializedName("popularity")
    @Expose
    val popularity: Float = 0.0F,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "no",
    @SerializedName("release_date")
    @Expose
    val releaseDate: String = "no",
    @SerializedName("title")
    @Expose
    val title: String = "no",
    @SerializedName("video")
    @Expose
    val video: Boolean = false,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: String = "no",
    @SerializedName("vote_count")
    @Expose
    val voteCount: Long = 0L,
    val isEmpty: Boolean = false
)
