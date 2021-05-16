package com.example.kino.network.model.movie

import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Actors(
    @SerializedName("id")
    @Expose
    val id: Int = 0,
    @SerializedName("cast")
    @Expose
    val cast: List<Cast> = emptyList(),
): NetworkItem
