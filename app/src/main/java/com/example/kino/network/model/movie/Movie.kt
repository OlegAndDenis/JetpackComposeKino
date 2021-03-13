package com.example.kino.network.model.movie

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("page")
    @Expose
    val page: Int = 0,
    @SerializedName("results")
    @Expose
    val result: List<MovieResult>,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0,
    val isEmpty: Boolean = false
)
