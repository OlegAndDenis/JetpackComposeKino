package com.example.kino.network.model.movie

import com.example.kino.network.model.common.GenresApi
import com.example.kino.network.model.common.Images
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.common.Videos
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("adult")
    @Expose
    val adult: Boolean = false,
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String = "",
    @SerializedName("belongs_to_collection")
    @Expose
    val belongsToCollections: BelongsToCollections = BelongsToCollections(isEmpty = true),
    @SerializedName("budget")
    @Expose
    val budget: Long = 0,
    @SerializedName("genres")
    @Expose
    val genres: List<GenresApi> = emptyList(),
    @SerializedName("homepage")
    @Expose
    val homepage: String = "",
    @SerializedName("id")
    @Expose
    val id: Long = 0,
    @SerializedName("imdb_id")
    @Expose
    val imdbId: String = "",
    @SerializedName("original_language")
    @Expose
    val originalLanguage: String = "",
    @SerializedName("original_title")
    @Expose
    val originalTitle: String = "",
    @SerializedName("title")
    @Expose
    val title: String = "",
    @SerializedName("overview")
    @Expose
    val overview: String = "",
    @SerializedName("popularity")
    @Expose
    val popularity: Float = 0.0F,
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "",
    @SerializedName("production_companies")
    @Expose
    val productionCompanies: List<ProductionCompanies> = emptyList(),
    @SerializedName("production_countries")
    @Expose
    val productionCountries: List<ProductionCountries> = emptyList(),
    @SerializedName("release_date")
    @Expose
    val releaseDate: String = "",
    @SerializedName("revenue")
    @Expose
    val revenue: Long = 0L,
    @SerializedName("runtime")
    @Expose
    val runtime: Int = 0,
    @SerializedName("status")
    @Expose
    val status: String = "",
    @SerializedName("tagline")
    @Expose
    val tagline: String = "",
    @SerializedName("video")
    @Expose
    val video: Boolean = false,
    @SerializedName("vote_average")
    @Expose
    val voteAverage: Double = 0.0,
    @SerializedName("vote_count")
    @Expose
    val voteCount: Double = 0.0,
    @SerializedName("images")
    @Expose
    val images: Images = Images(),
    @SerializedName("videos")
    @Expose
    val videos: Videos = Videos(),
    ): NetworkItem