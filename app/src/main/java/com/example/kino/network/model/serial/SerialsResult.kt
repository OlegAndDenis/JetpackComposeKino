package com.example.kino.network.model.serial

import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SerialsResult(
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String = "no",
    @SerializedName("id")
    @Expose
    val id: Long = 0L,
    @SerializedName("genre_ids")
    @Expose
    val genreIds: List<Long> = listOf(),
    @SerializedName("original_name")
    @Expose
    val originalName: String = "no",
    @SerializedName("overview")
    @Expose
    val overview: String = "no",
    @SerializedName("popularity")
    @Expose
    val popularity: Float = 0.0F,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "no",
    @SerializedName("first_air_date")
    @Expose
    val firstAirDate: String = "no",
    @SerializedName("name")
    @Expose
    val name: String = "no",
    @SerializedName("video")
    @Expose
    val video: Boolean = false,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: String = "no",
    @SerializedName("vote_count")
    @Expose
    val voteCount: Long = 0L,
    @SerializedName("origin_country")
    @Expose
    val originCountry: List<String> = emptyList(),
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "no",
    val isEmpty: Boolean = false
): NetworkItem
