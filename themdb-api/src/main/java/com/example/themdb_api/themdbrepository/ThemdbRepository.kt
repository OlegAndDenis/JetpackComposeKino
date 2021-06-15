package com.example.themdb_api.themdbrepository

import com.example.themdb_api.GenresApi
import com.example.themdb_api.MovieApi

interface ThemdbRepository {

    suspend fun getListGenresByMovie() : GenresApi

    suspend fun getMovieByGenres(genresId: String): MovieApi

    suspend fun getPopularityMove(): MovieApi
}