package com.example.kino.network.model.serial

import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CreateBy(
    @SerializedName("id")
    @Expose
    val id: Long = 0,
    @SerializedName("credit_id")
    @Expose
    val createdId: Long = 0,
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("gender")
    @Expose
    val gender: Int = 0,
    @SerializedName("profile_path")
    @Expose
    val profilePatch: String = "",
): NetworkItem
