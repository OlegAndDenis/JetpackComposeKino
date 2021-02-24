package com.example.kino.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenresList(
    @SerializedName("genres")
    @Expose
    var genres : List<GenresApi>
)
