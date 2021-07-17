package com.example.themdb_api.common

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    @SerialName("page")
    val page: Int = 0,
    @SerialName("results")
    val result: List<Type>,
    @SerialName("total_pages")
    val totalPages: Int = 0,
    @SerialName("total_results")
    val totalResults: Int = 0,
    val isEmpty: Boolean = false
)