package com.example.kino.network.model.search

import com.example.kino.MultiDeserializer
import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("page")
    @Expose
    val page: Int = 0,
    @SerializedName("results")
    @Expose
    @JsonAdapter(nullSafe = true, value = MultiDeserializer::class)
    val result: List<NetworkItem>,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0,
    val isEmpty: Boolean = false
) {
}