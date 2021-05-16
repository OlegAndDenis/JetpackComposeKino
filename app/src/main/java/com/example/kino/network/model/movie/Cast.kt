package com.example.kino.network.model.movie

import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Cast(
    @SerializedName("adult")
    @Expose
    val adult: Boolean = false,
    @SerializedName("gender")
    @Expose
    val gender: Int = 0,
    @SerializedName("id")
    @Expose
    val id: Int = 0,
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String = "",
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("original_name")
    @Expose
    val original_name: String = "",
    @SerializedName("profile_path")
    @Expose
    val profilePath: String = "",
    @SerializedName("cast_id")
    @Expose
    val castId: Int = 0,
    @SerializedName("character")
    @Expose
    val character: String = "",
    @SerializedName("credit_id")
    @Expose
    val creditId: String = "",
    @SerializedName("order")
    @Expose
    val order: Int = -1,
) : NetworkItem