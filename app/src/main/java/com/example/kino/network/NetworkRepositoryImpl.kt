package com.example.kino.network

import android.annotation.SuppressLint
import com.example.kino.applicationm.MovieApplication
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkRepository.*
import com.example.kino.network.model.GenresList
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class NetworkRepositoryImpl(
    private val api: Api,
    private val databaseRepository: DatabaseRepository
) : NetworkRepository {

    private val START_RESULT: String = "START"
    private val API_KEY = "620da4379b4594c225da04326f92ffb1"

    private lateinit var mResultSuccess: ResultSuccess

    override fun isDownloadGenres(result: ResultSuccess) {
         downloadGenresAll()
        mResultSuccess = result
    }

    override fun isNotEmptyDB() : Boolean =
        databaseRepository.isNotEmptyGenresAll().isNotEmpty

    override fun getListItems() {
        TODO("Not yet implemented")
    }

    @SuppressLint("CheckResult")
    private fun downloadGenresAll() {

        val movie: Single<GenresList> = api.getGenres("movie", API_KEY, MovieApplication.language)
        val serail: Single<GenresList> = api.getGenres("tv", API_KEY, MovieApplication.language)

        Single.zip(movie, serail, { movies, serials -> Pair(movies,serials) })
            .subscribeOn(Schedulers.io())
            .subscribe({
                databaseRepository.insertGenres(it.first.genres, "movie")
                databaseRepository.insertGenres(it.second.genres, "tv")
                mResultSuccess.setSuccess(START_RESULT)
            },Timber::e)
    }
}