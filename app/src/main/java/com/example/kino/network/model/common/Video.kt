package com.example.kino.network.model.common

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    @SerializedName("id")
    @Expose
    val id: String = "",
    @SerializedName("iso_3166_1")
    @Expose
    val iso: String = "",
    @SerializedName("key")
    @Expose
    val key: String = "",
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("site")
    @Expose
    val site: String = "",
    @SerializedName("size")
    @Expose
    val size: String = "",
    @SerializedName("type")
    @Expose
    val type: String = "",
): NetworkItem, Parcelable
