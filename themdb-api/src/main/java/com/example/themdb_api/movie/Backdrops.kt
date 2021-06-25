package com.example.themdb_api.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Backdrops(
    @SerialName("file_path")
    var file_path: String = "",
    @SerialName("vote_count")
    var voteCount: Int = 0
)
