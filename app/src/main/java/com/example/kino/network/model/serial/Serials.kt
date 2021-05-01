package com.example.kino.network.model.serial

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Serials(
    @SerializedName("page")
    @Expose
    val page: Int = 0,
    @SerializedName("results")
    @Expose
    val result: List<SerialsResult>,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0,
    val isEmpty: Boolean = false
)
