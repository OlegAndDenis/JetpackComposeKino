package com.example.kino.network.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Posters(
    @SerializedName("file_path")
    @Expose
    var file_path: String = "",
    @SerializedName("vote_count")
    @Expose
    var voteCount: Int = 0
): NetworkItem
