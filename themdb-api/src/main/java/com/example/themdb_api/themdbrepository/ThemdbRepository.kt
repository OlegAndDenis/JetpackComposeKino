package com.example.themdb_api.themdbrepository

import com.example.themdb_api.genres.GenresApi
import com.example.themdb_api.movie.MovieApi

interface ThemdbRepository {

    suspend fun getListGenresByMovie() : GenresApi

    suspend fun getMovieByGenres(genresId: String): MovieApi

    suspend fun getPopularityMove(): MovieApi
}