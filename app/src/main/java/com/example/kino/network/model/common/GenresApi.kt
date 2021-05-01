package com.example.kino.network.model.common

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenresApi(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    val name: String = ""
)
