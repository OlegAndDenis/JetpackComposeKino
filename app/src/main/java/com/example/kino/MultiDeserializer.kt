package com.example.kino

import com.example.kino.network.model.person.PersonResult
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.network.model.serial.SerialsResult
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MultiDeserializer : JsonDeserializer<Map<NetworkItem, List<NetworkItem>>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Map<NetworkItem, List<NetworkItem>> {
        val gson = Gson()
        val movie = mutableListOf<NetworkItem>()
        val serials = mutableListOf<NetworkItem>()
        val persons = mutableListOf<NetworkItem>()
        json?.asJsonArray?.forEach { cObject ->
            when (cObject.asJsonObject.get("media_type").asString) {
                "movie" -> movie.add(gson.fromJson(cObject, MovieResult::class.java))
                "tv" -> serials.add(gson.fromJson(cObject, SerialsResult::class.java))
                "person" -> persons.add(gson.fromJson(cObject, PersonResult::class.java))
            }
        }
        val map = mutableMapOf<NetworkItem, List<NetworkItem>>()
        map[TitleInRecycler("Фильмы", movie.size)] = movie
        map[TitleInRecycler("Сериалы", serials.size)] = serials
        map[TitleInRecycler("Актер", persons.size)] = persons
        return map
    }
}