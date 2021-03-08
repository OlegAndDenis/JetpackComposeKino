package com.example.kino.network

import com.example.kino.network.model.GenresList
import com.example.kino.network.model.Movie
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {
    @GET("genre/{type}/list?")
    fun getGenres(@Path("type") type : String, @Query("api_key") api_key: String,
                  @Query("language") language: String) : Single<GenresList>

    @GET("discover/{type}")
    fun getMovie(@Path("type") type: String, @QueryMap param: MutableMap<String, String>): Single<Movie>
}