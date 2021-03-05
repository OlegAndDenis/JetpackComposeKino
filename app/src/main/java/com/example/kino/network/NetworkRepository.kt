package com.example.kino.network

interface NetworkRepository {
    fun isDownloadGenres(result: ResultSuccess)

    fun getListItems()

    fun isNotEmptyDB() : Boolean


    interface ResultSuccess {
        fun setSuccess(result: String)
    }
}