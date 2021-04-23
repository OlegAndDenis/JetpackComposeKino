package com.example.kino.network.model.person

import com.example.kino.MultiDeserializer
import com.example.kino.network.model.common.NetworkItem
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class PersonResult(
    @SerializedName("adult")
    @Expose
    val adult: Boolean = false,
    @SerializedName("gender")
    @Expose
    val gender: Int = -1,
    @SerializedName("id")
    @Expose
    val id: Long = -1,
    @SerializedName("known_for")
    @Expose
    @JsonAdapter(nullSafe = true, value = MultiDeserializer::class)
    val knownFor: Map<NetworkItem, List<NetworkItem>>,
    @SerializedName("known_for_department")
    @Expose
    val knownForDepartment: String = "",
    @SerializedName("media_type")
    @Expose
    val mediaType: String = "",
    @SerializedName("name")
    @Expose
    val name: String = "",
    @SerializedName("popularity")
    @Expose
    val popularity: Double = 0.0,
    @SerializedName("profile_path")
    @Expose
    val profilePath: String = ""
): NetworkItem {
    override fun toString(): String {
        return "PersonResult [$id, $name]"
    }
}
