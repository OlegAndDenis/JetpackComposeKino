package com.example.kino.network

import com.example.kino.applicationm.MovieApplication
import com.example.kino.network.model.common.GenresList
import com.example.kino.network.model.movie.Actors
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.network.model.search.SearchResult
import com.example.kino.network.model.serial.Serials
import com.example.kino.screen.common.typeenum.TypeEnum.*
import java.lang.Exception

class NetworkRepositoryImpl(
    private val api: Api,
) : NetworkRepository {

    private val API_KEY = "620da4379b4594c225da04326f92ffb1"

    override suspend fun downloadGenres() : Pair<GenresList, GenresList> {
        val movie = try {
            api.getGenres(MOVIE.type, API_KEY, MovieApplication.language)
        } catch (e: Exception) {
            return Pair(GenresList(emptyList()), GenresList(emptyList()))
        }

        val serial = try {
            api.getGenres(TV.type, API_KEY, MovieApplication.language)
        } catch (e: Exception) {
            return Pair(GenresList(emptyList()), GenresList(emptyList()))
        }
        return Pair(movie, serial)
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

    override suspend fun getSerials(page: Int): Serials =
        api.getSerials(buildParamSerials(page))

    override suspend fun getSearch(query: String, page: Int): SearchResult =
        api.getSearch(buildParamSearch(page, query))

    override suspend fun getActors(idMovie: String): Actors =
        api.getActor(idMovie, buildActor())

    override suspend fun getRotate(page: Int): Movie =
        api.getRotate(buildParamPopularity(page))

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