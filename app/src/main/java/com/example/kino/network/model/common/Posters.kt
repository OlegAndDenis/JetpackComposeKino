package com.example.kino.network.model.common

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Posters(
    @SerializedName("file_path")
    @Expose
    var file_path: String = "",
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int = 0
): NetworkItem, Parcelable
