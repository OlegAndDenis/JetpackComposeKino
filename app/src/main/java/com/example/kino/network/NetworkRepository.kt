package com.example.kino.network

import androidx.work.ListenableWorker.*

interface NetworkRepository {
    fun isDownloadGenres(): Result

    fun getListItems()

    fun isNotEmptyDB() : Boolean
}