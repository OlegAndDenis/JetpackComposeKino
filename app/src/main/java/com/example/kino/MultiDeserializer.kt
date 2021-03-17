package com.example.kino

import android.util.Log
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.network.model.serial.SerialsResult
import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class MultiDeserializer : JsonDeserializer<List<NetworkItem>> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): List<NetworkItem> {
        val gson = Gson()
        val list = mutableListOf<NetworkItem>()
        if (json != null) {
            Log.i("OLEG", "$json")
            for (cObject in json.asJsonArray) {
                when (cObject.asJsonObject.get("media_type").asString) {
                    "movie" -> list.add(gson.fromJson(cObject, MovieResult::class.java))
                    "tv" -> list.add(gson.fromJson(cObject, SerialsResult::class.java))
                }
            }
        }
        return list
    }
}