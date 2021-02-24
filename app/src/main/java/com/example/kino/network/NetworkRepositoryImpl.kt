package com.example.kino.network

import android.annotation.SuppressLint
import android.util.Log
import androidx.work.Data
import androidx.work.ListenableWorker.*
import com.example.kino.applicationm.MovieApplication
import com.example.kino.db.DatabaseRepository
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class NetworkRepositoryImpl(
    private val api: Api,
    private val databaseRepository: DatabaseRepository
) :
    NetworkRepository {

    private val WORK_RESULT = "work_result"
    private val API_KEY = "620da4379b4594c225da04326f92ffb1"

    override fun isDownloadGenres(): Result {
        return downloadGenresAll()
    }

    override fun isNotEmptyDB() : Boolean =
        databaseRepository.isNotEmptyGenresAll().isNotEmpty

    override fun getListItems() {
        TODO("Not yet implemented")
    }

    @SuppressLint("CheckResult")
    private fun downloadGenresAll(): Result {
        api.getGenres("movie", API_KEY, MovieApplication.language)
            .subscribeOn(Schedulers.io())
            .subscribe({
                databaseRepository.insertGenres(it.genres, "movie")
            }, {
                Timber.e(it)
            })

        api.getGenres("tv", API_KEY, MovieApplication.language)
            .subscribeOn(Schedulers.io())
            .subscribe({
                databaseRepository.insertGenres(it.genres, "tv")
            }, {
                Timber.e(it)
            })
        return Result.success(Data.Builder().putString(WORK_RESULT, "finish").build())
    }
}