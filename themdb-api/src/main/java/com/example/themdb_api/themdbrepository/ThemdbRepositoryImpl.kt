package com.example.themdb_api.themdbrepository

import com.example.themdb_api.BuildConfig
import com.example.themdb_api.api.ApiClient
import com.example.themdb_api.genres.GenresApi
import com.example.themdb_api.movie.MovieApi
import com.example.themdb_api.serials.SerialApi
import javax.inject.Inject

class ThemdbRepositoryImpl @Inject constructor(
    private val clientApi: ApiClient
) : ThemdbRepository {

    private val typeFilm: String = "movie"
    private val typeTv: String = "tv"

    override suspend fun getListGenresByMovie(): GenresApi =
        clientApi.getGenres(typeFilm, BuildConfig.TMDB_API_KEY, "ru-RU")

    override suspend fun getMovieByGenres(genresId: String): MovieApi =
        clientApi.getMovieByGenres("movie", buildParamFilm(genresId))

    override suspend fun getPopularityMove(): MovieApi =
        clientApi.getPopularityMovie("movie", buildParamPopularity())

    override suspend fun getSerials(genresId: String): SerialApi =
        clientApi.getSerialByGenres("tv", buildParamFilm(genresId))

    override suspend fun getPopularSerial(): SerialApi =
        clientApi.getPopularitySerial("tv", buildParamPopularity())

    private fun buildParamFilm(genres: String): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = BuildConfig.TMDB_API_KEY
        map["language"] = "ru-RU"
        map["with_genres"] = genres
        map["sort_by"] = "popularity.desc"
        map["page"] = "1"
        return map
    }

    private fun buildParamPopularity(): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = BuildConfig.TMDB_API_KEY
        map["language"] = "ru-RU"
        map["page"] = "1"
        return map
    }
}