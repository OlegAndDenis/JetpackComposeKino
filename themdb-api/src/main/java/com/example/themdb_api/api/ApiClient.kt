package com.example.themdb_api.api

import com.example.themdb_api.GenresApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {

    @GET("genre/{type}/list?")
    suspend fun getGenres(@Path("type") type : String, @Query("api_key") api_key: String,
                  @Query("language") language: String): GenresApi
}