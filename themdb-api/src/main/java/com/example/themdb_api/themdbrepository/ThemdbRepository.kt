package com.example.themdb_api.themdbrepository

import com.example.themdb_api.GenresApi

interface ThemdbRepository {

    suspend fun getListGenresByMovie() : GenresApi

    fun getMovieByGenres(genresId: Long)

    fun getPopularityMove()
}