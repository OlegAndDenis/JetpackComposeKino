package com.example.themdb_api.themdbrepository

import com.example.themdb_api.GenresApi
import com.example.themdb_api.api.ApiClient
import javax.inject.Inject

class ThemdbRepositoryImpl @Inject constructor(
    private val clientApi: ApiClient
) : ThemdbRepository {

    private val API_KEY = "620da4379b4594c225da04326f92ffb1"
    private val typeFilm: String = "movie"

    override suspend fun getListGenresByMovie(): GenresApi =
        clientApi.getGenres(typeFilm, API_KEY, "ru-RU")

    override fun getMovieByGenres(genresId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPopularityMove() {
        TODO("Not yet implemented")
    }
}