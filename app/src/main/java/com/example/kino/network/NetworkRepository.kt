package com.example.kino.network

import com.example.kino.network.model.search.SearchResult
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.network.model.serial.Serials
import com.example.kino.screen.common.ContainerId
import io.reactivex.Single

interface NetworkRepository {
    fun isDownloadGenres(result: ResultSuccess)

    fun getListItems()

    fun getFilm(page: Int, genres: String): Single<Movie>

    fun getSerials(page: Int): Single<Serials>

    fun getSearch(query: String, page: Int): Single<SearchResult>

    fun getMovie(id: String): Single<MovieResult>

    fun isOnline(): Boolean

    interface ResultSuccess {
        fun success(result: NetworkEnum)
    }
}