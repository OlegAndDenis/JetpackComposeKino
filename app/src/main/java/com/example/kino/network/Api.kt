package com.example.kino.network

import com.example.kino.network.model.GenresList
import com.example.kino.network.model.SearchResult
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.serial.Serials
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {
    @GET("genre/{type}/list?")
    fun getGenres(@Path("type") type : String, @Query("api_key") api_key: String,
                  @Query("language") language: String) : Single<GenresList>

    @GET("discover/movie")
    fun getFilm(@QueryMap param: MutableMap<String, String>): Single<Movie>

    @GET("discover/tv")
    fun getSerials(@QueryMap param: MutableMap<String, String>): Single<Serials>

    @GET("search/multi")
    fun getSearch(@QueryMap param: MutableMap<String, String>): Single<SearchResult>
}