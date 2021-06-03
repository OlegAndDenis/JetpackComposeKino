package com.example.kino.network.model.movie

import android.os.Parcelable
import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductionCompanies(
    @SerializedName("id")
    @Expose
    val id: Long = 0,
    @SerializedName("logo_path")
    @Expose
    val logoPath: String = "",
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("origin_country")
    @Expose
    val originalCountry: String = "",
): NetworkItem, Parcelable
