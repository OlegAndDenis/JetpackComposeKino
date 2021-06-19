package com.example.themdb_api.serials

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SerialApi(
    @SerialName("page")
    val page: Int = 0,
    @SerialName("results")
    val result: List<SerialResult>,
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0,
)
