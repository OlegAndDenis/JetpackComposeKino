package com.example.kino.network.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("results")
    @Expose
    val videos: List<Video> = emptyList()
): NetworkItem
