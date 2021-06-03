package com.example.kino.network.model.common

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Videos(
    @SerializedName("results")
    @Expose
    val videos: List<Video> = emptyList()
): NetworkItem, Parcelable
