package com.example.kino.network

import android.annotation.SuppressLint
import android.content.Context
import com.example.kino.applicationm.MovieApplication
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkRepository.*
import com.example.kino.network.model.common.GenresList
import com.example.kino.network.model.SearchResult
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.serial.Serials
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class NetworkRepositoryImpl(
    private val context: Context,
    private val api: Api,
    private val databaseRepository: DatabaseRepository
) : NetworkRepository {

    private val START_RESULT: String = "START"
    private val NO_CONNECTION_NETWORK = "NO_CONNECTION"
    private val ERROR = "ERROR"
    private val API_KEY = "620da4379b4594c225da04326f92ffb1"
    private val typeFilm: String = "movie"
    private val typeTv: String = "tv"

    override fun isDownloadGenres(result: ResultSuccess) {
        when (isOnline()) {
            true -> {
                downloadGenresAll(result)
            }
            false -> {
                isNotEmptyDB(result)
            }
        }
    }

    @SuppressLint("CheckResult")
    fun isNotEmptyDB(result: ResultSuccess) {
        databaseRepository.isNotEmptyGenresAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isNotEmpty) {
                    result.setSuccess(START_RESULT)
                } else {
                    result.setSuccess(NO_CONNECTION_NETWORK)
                }
            }, {
                result.setSuccess(ERROR)
                Timber.e(it)
            })
    }

    override fun getListItems() {
        TODO("Not yet implemented")
    }

    override fun getFilm(page: Int): Single<Movie> {
        return api.getFilm(buildParamFilm(page))
    }

    override fun getSerials(page: Int): Single<Serials> {
        return api.getSerials(buildParamSerials(page))
    }

    override fun getSearch(query: String): Single<SearchResult> {
        return api.getSearch(buildParamSearch(1, query))
    }

    @SuppressLint("CheckResult")
    private fun downloadGenresAll(resultSuccess: ResultSuccess) {
        val movie: Single<GenresList> = api.getGenres(typeFilm, API_KEY, MovieApplication.language)
        val serial: Single<GenresList> = api.getGenres(typeTv, API_KEY, MovieApplication.language)

        Single.zip(movie, serial, { movies, serials -> Pair(movies, serials) })
            .subscribeOn(Schedulers.io())
            .subscribe({
                databaseRepository.insertGenres(it.first.genres, typeFilm)
                databaseRepository.insertGenres(it.second.genres, typeTv)
                resultSuccess.setSuccess(START_RESULT)
            }, {
                resultSuccess.setSuccess(ERROR)
                Timber.e(it)
            })
    }

    override fun isOnline(): Boolean = ConnectionCheck.isOnline(context)

    private fun buildParamFilm(page: Int): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = API_KEY
        map["language"] = MovieApplication.language
        map["sort_by"] = "popularity.desc"
        map["page"] = page.toString()
        return map
    }

    private fun buildParamSerials(page: Int): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = API_KEY
        map["language"] = MovieApplication.language
        map["sort_by"] = "popularity.desc"
        map["timezone"] = "popularity.desc"
        map["page"] = page.toString()
        return map
    }

    private fun buildParamSearch(page: Int, query: String): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = API_KEY
        map["language"] = MovieApplication.language
        map["query"] = query
        map["page"] = page.toString()
        return map
    }
}