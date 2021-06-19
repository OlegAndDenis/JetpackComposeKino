package com.example.themdb_api.api

import com.example.themdb_api.genres.GenresApi
import com.example.themdb_api.movie.MovieApi
import com.example.themdb_api.serials.SerialApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ApiClient {

    @GET("genre/{type}/list?")
    suspend fun getGenres(
        @Path("type") type: String, @Query("api_key") api_key: String,
        @Query("language") language: String
    ): GenresApi

    @GET("discover/{type}")
    suspend fun getMovieByGenres(
        @Path("type") type: String,
        @QueryMap param: MutableMap<String, String>
    ): MovieApi

    @GET("{type}/popular")
    suspend fun getPopularityMovie(
        @Path("type") type: String,
        @QueryMap param: MutableMap<String, String>
    ): MovieApi

    @GET("discover/{type}")
    suspend fun getSerialByGenres(
        @Path("type") type: String,
        @QueryMap param: MutableMap<String, String>
    ): SerialApi

    @GET("{type}/popular")
    suspend fun getPopularitySerial(
        @Path("type") type: String,
        @QueryMap param: MutableMap<String, String>
    ): SerialApi
}