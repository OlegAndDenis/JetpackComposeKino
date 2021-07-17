package com.example.themdb_api.themdbrepository

import com.example.themdb_api.BuildConfig
import com.example.themdb_api.api.ApiClient
import com.example.themdb_api.common.SearchResult
import com.example.themdb_api.genres.GenresApi
import com.example.themdb_api.movie.DetailMovie
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

    //Fixme добавить получение типа как парамметр. Добавить Enum для TV и Movie
    override suspend fun getListGenresBySerial(): GenresApi =
        clientApi.getGenres(typeTv, BuildConfig.TMDB_API_KEY, "ru-RU")

    override suspend fun getMovieByGenres(genresId: String): MovieApi =
        clientApi.getMovieByGenres("movie", buildParamFilm(genresId))

    override suspend fun getPopularityMove(): MovieApi =
        clientApi.getPopularityMovie("movie", buildParamPopularity())

    override suspend fun getSerials(genresId: String): SerialApi =
        clientApi.getSerialByGenres("tv", buildParamFilm(genresId))

    override suspend fun getPopularSerial(): SerialApi =
        clientApi.getPopularitySerial("tv", buildParamPopularity())

    override suspend fun loadDetail(id: String): DetailMovie =
        clientApi.getMovie(id, buildDetail())

    override suspend fun getSearchingResult(query: String): SearchResult =
        clientApi.getSearch(buildParamSearch(1, query))

    private fun buildParamSearch(page: Int, query: String): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = BuildConfig.TMDB_API_KEY
        map["language"] = "ru-RU"
        map["query"] = query
        map["page"] = page.toString()
        return map
    }

    private fun buildParamFilm(genres: String): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = BuildConfig.TMDB_API_KEY
        map["language"] = "ru-RU"
        map["with_genres"] = genres
        map["sort_by"] = "popularity.desc"
        map["page"] = "1"
        return map
    }

    private fun buildDetail(): MutableMap<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        map["api_key"] = BuildConfig.TMDB_API_KEY
        map["language"] = "ru-RU"
        map["append_to_response"] = "videos,images"
        map["include_image_language"] = "ru-RU,null"
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