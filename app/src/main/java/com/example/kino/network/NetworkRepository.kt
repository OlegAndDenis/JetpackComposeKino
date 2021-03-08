package com.example.kino.network

interface NetworkRepository {
    fun isDownloadGenres(result: ResultSuccess)

    fun getListItems()

    interface ResultSuccess {
        fun setSuccess(result: String)
    }
}