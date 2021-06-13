package com.example.themdb_api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenresApi(
    @SerializedName("genres")
    @Expose
    var genres: List<Genres>
)
