package com.example.themdb_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Genres(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    val name: String = ""
)
