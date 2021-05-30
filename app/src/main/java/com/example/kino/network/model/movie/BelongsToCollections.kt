package com.example.kino.network.model.movie

import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BelongsToCollections(
    @SerializedName("id")
    @Expose
    val id: Int = 0,
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("poster_path")
    @Expose
    val posterPath: String = "",
    @SerializedName("backdrop_path")
    @Expose
    val backdropPath: String = "",

    val isEmpty: Boolean = false
): NetworkItem