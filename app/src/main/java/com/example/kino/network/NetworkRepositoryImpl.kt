package com.example.kino.network

import android.annotation.SuppressLint
import android.content.Context
import com.example.kino.screen.common.TypeEnum.*
import com.example.kino.applicationm.MovieApplication
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkEnum.*
import com.example.kino.network.NetworkRepository.*
import com.example.kino.network.model.common.GenresList
import com.example.kino.network.model.movie.Actors
import com.example.kino.network.model.search.SearchResult
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.network.model.serial.Serials
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class NetworkRepositoryImpl(
    private val context: Context,
    private val api: Api,
    private val databaseRepository: DatabaseRepository,
) : NetworkRepository {

    private val API_KEY = "620da4379b4594c225da04326f92ffb1"

    override fun isDownloadGenres(result: ResultSuccess) {
        when (isOnline()) {
            true -> downloadGenresAll(result)
            false -> isNotEmptyDB(result)
        }
    }

    @SuppressLint("CheckResult")
    private fun downloadGenresAll(resultSuccess: ResultSuccess) {
        val movie: Single<GenresList> =
            api.getGenres(MOVIE.type, API_KEY, MovieApplication.language).retry(3)
        val serial: Single<GenresList> =
            api.getGenres(TV.type, API_KEY, MovieApplication.language).retry(3)

        Single.zip(movie, serial, { movies, serials -> Pair(movies, serials) })
            .subscribeOn(Schedulers.io())
            .subscribe({
                databaseRepository.insertGenres(it.first.genres, MOVIE)
                databaseRepository.insertGenres(it.second.genres, TV)
                resultSuccess.success(OK)
            }, {
                resultSuccess.success(ERROR)
                Timber.e(it)
            })
    }

    @SuppressLint("CheckResult")
    fun isNotEmptyDB(result: ResultSuccess) {
        databaseRepository.isNotEmptyGenresAll()
            .retry(3)
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it.isNotEmpty) {
                    result.success(OK)
                } else {
                    result.success(NO_CONNECTION)
                }
            }, {
                result.success(ERROR)
                Timber.e(it)
            })
    }

    override fun getListItems() {
        TODO("Not yet implemented")
    }

    override suspend fun getMovie(id: String): MovieDetail =
        api.getMovie(id, buildParamMovie())

    override suspend fun getFilm(page: Int, genres: String): Movie =
        api.getFilm(buildParamFilm(page, genres))

    override suspend fun getPopularity(page: Int): Movie =
        api.getPopularity(buildParamPopularity(page))

    override fun getSerials(page: Int): Single<Serials> =
        api.getSerials(buildParamSerials(page))

    override fun getSearch(query: String, page: Int): Single<SearchResult> =
        api.getSearch(buildParamSearch(page, query))

    override suspend fun getActors(idMovie: String): Actors =
        api.getActor(idMovie, buildActor())

    override suspend fun getRotate(page: Int): Movie =
        api.getRotate(buildParamPopularity(page))

    override fun isOnline(): Boolean = ConnectionCheck.isOnline(context)

    private fun buildParamFilm(page: Int, genres: String): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = API_KEY
        map["language"] = MovieApplication.language
        map["with_genres"] = genres
        map["sort_by"] = "popularity.desc"
        map["page"] = page.toString()
        return map
    }

    private fun buildParamPopularity(page: Int): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = API_KEY
        map["language"] = MovieApplication.language
        map["page"] = page.toString()
        return map
    }

    private fun buildParamMovie(): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = API_KEY
        map["language"] = MovieApplication.language
        map["append_to_response"] = "videos,images"
        map["include_image_language"] = "${MovieApplication.language},null"
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

    private fun buildActor(): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = API_KEY
        map["language"] = MovieApplication.language
        return map
    }
}