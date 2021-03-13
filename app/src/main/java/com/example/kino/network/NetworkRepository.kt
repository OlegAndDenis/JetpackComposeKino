package com.example.kino.network

import com.example.kino.network.model.Movie
import io.reactivex.Single

interface NetworkRepository {
    fun isDownloadGenres(result: ResultSuccess)

    fun getListItems()

    fun getMovie(page: Int, type: String): Single<Movie>

    interface ResultSuccess {
        fun setSuccess(result: String)
    }
}