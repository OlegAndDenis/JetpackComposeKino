package com.example.kino.network.model.movie

import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProductionCountries(
    @SerializedName("iso_3166_1")
    @Expose
    val iso: String = "",
    @SerializedName("name")
    @Expose
    val name: String = "",
): NetworkItem
