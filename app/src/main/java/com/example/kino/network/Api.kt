package com.example.kino.network

import com.example.kino.network.model.common.GenresList
import com.example.kino.network.model.movie.Actors
import com.example.kino.network.model.search.SearchResult
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.network.model.serial.Serials
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface Api {
    @GET("genre/{type}/list?")
    suspend fun getGenres(
        @Path("type") type: String, @Query("api_key") api_key: String,
        @Query("language") language: String,
    ): GenresList

    @GET("discover/movie")
    suspend fun getFilm(@QueryMap param: MutableMap<String, String>): Movie

    @GET("movie/popular")
    suspend fun getPopularity(@QueryMap param: MutableMap<String, String>): Movie

    @GET("movie/top_rated")
    suspend fun getRotate(@QueryMap param: MutableMap<String, String>): Movie

    @GET("movie/now_playing")
    suspend fun getNewPlaying(@QueryMap param: MutableMap<String, String>): Movie

    @GET("discover/tv")
    suspend fun getSerials(@QueryMap param: MutableMap<String, String>): Serials

    @GET("search/multi")
    suspend fun getSearch(@QueryMap param: MutableMap<String, String>): SearchResult

    @GET("movie/{idMovie}/credits")
    suspend fun getActor(
        @Path("idMovie") idMovie: String,
        @QueryMap param: MutableMap<String, String>,
    ): Actors

    @GET("movie/{movie_id}")
   suspend fun getMovie(
        @Path("movie_id") id: String,
        @QueryMap param: MutableMap<String, String>,
    ): MovieDetail
}