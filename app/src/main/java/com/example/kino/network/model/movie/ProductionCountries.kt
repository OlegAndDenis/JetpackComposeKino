package com.example.kino.network.model.movie

import android.os.Parcelable
import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCountries(
    @SerializedName("iso_3166_1")
    @Expose
    val iso: String = "",
    @SerializedName("name")
    @Expose
    val name: String = "",
): NetworkItem, Parcelable
