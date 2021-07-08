package com.example.themdb_api.common

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

val module = SerializersModule {
    polymorphic(Type::class) {
        subclass(SerialResult::class)
        subclass(MovieResult::class)
        subclass(PersonResult::class)
    }
}