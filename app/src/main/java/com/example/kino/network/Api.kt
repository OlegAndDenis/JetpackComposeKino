package com.example.kino.network

import com.example.kino.network.model.GenresList
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("genre/{type}/list?")
    fun getGenres(@Path("type") type : String, @Query("api_key") api_key: String,
                  @Query("language") language: String) : Observable<GenresList>
}