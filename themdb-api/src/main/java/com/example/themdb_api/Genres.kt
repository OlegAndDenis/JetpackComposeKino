package com.example.themdb_api

import kotlinx.serialization.Serializable

@Serializable
data class Genres(
    var id: Int,
    val name: String = ""
)
