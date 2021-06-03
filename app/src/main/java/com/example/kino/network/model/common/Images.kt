package com.example.kino.network.model.common

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Images(
    @SerializedName("backdrops")
    @Expose
    val backdrops: List<Backdrops> = emptyList(),
    @SerializedName("posters")
    @Expose
    val posters: List<Posters> = emptyList(),
): NetworkItem, Parcelable
