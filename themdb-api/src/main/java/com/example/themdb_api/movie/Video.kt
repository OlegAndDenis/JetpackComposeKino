package com.example.themdb_api.movie

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Video(
    @SerialName("id")
    val id: String = "",
    @SerialName("iso_639_1")
    val iso_639_1: String = "",
    @SerialName("iso_3166_1")
    val iso_3166_1: String = "",
    @SerialName("key")
    val key: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("site")
    val site: String = "",
    @SerialName("size")
    val size: Int = 0,
    @SerialName("type")
    val type: String = "",
)
