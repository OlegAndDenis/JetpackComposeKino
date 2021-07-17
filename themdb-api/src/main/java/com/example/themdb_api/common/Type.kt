package com.example.themdb_api.common

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Polymorphic
@Serializable
abstract class Type {
    abstract val mediaType: String
}
