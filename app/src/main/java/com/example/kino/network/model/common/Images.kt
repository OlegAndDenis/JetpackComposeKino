package com.example.kino.network.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Images(
    @SerializedName("backdrops")
    @Expose
    val backdrops: List<Backdrops> = emptyList(),
    @SerializedName("posters")
    @Expose
    val posters: List<Posters> = emptyList(),
): NetworkItem
