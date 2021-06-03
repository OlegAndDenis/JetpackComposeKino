package com.example.kino.network

import com.example.kino.network.model.movie.Actors
import com.example.kino.network.model.search.SearchResult
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.network.model.serial.Serials

interface NetworkRepository {
    suspend fun isDownloadGenres(result: ResultSuccess)

    fun getListItems()

    suspend fun getFilm(page: Int, genres: String): Movie

    suspend fun getPopularity(page: Int): Movie

    suspend fun getSerials(page: Int): Serials

    suspend fun getSearch(query: String, page: Int): SearchResult

    suspend fun getMovie(id: String): MovieDetail

    suspend fun getActors(idMovie: String): Actors

    suspend fun getRotate(page: Int): Movie

    fun isOnline(): Boolean

    interface ResultSuccess {
        fun success(result: NetworkEnum)
    }
}