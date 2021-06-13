package com.example.themdb_api.themdbrepository

import com.example.themdb_api.BuildConfig
import com.example.themdb_api.GenresApi
import com.example.themdb_api.api.ApiClient
import javax.inject.Inject

class ThemdbRepositoryImpl @Inject constructor(
    private val clientApi: ApiClient
) : ThemdbRepository {

    private val typeFilm: String = "movie"

    override suspend fun getListGenresByMovie(): GenresApi =
        clientApi.getGenres(typeFilm, BuildConfig.TMDB_API_KEY, "ru-RU")

    override fun getMovieByGenres(genresId: Long) {
        TODO("Not yet implemented")
    }

    override fun getPopularityMove() {
        TODO("Not yet implemented")
    }
}