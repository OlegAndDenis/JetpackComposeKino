package com.example.kino.network

import com.example.kino.network.model.movie.Actors
import com.example.kino.network.model.search.SearchResult
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.network.model.serial.Serials
import com.example.kino.screen.common.ContainerId
import io.reactivex.Single

interface NetworkRepository {
    fun isDownloadGenres(result: ResultSuccess)

    fun getListItems()

    suspend fun getFilm(page: Int, genres: String): Movie

    suspend fun getPopularity(page: Int): Movie

    fun getSerials(page: Int): Single<Serials>

    fun getSearch(query: String, page: Int): Single<SearchResult>

    fun getMovie(id: String): Single<MovieDetail>

    fun getActors(idMovie: String): Single<Actors>

    suspend fun getRotate(page: Int): Movie

    fun isOnline(): Boolean

    interface ResultSuccess {
        fun success(result: NetworkEnum)
    }
}