package com.example.themdb_api.common

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Polymorphic
@SerialName("person")
@Serializable
data class PersonResult(
    @SerialName("adult")
    val adult: Boolean = false,
    @SerialName("gender")
    val gender: Int = -1,
    @SerialName("id")
    val id: Long = -1,
    @SerialName("known_for")
    val knownFor: List<Type>,
    @SerialName("known_for_department")
    val knownForDepartment: String = "",
    @SerialName("name")
    val name: String = "",
    @SerialName("popularity")
    val popularity: Double = 0.0,
    @SerialName("profile_path")
    val profilePath: String = "",
    override val mediaType: String = "person",
) : Type()