package com.example.kino.screen.movie.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.db.DatabaseRepository
import com.example.kino.db.model.Genres
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.screen.common.model.GenresList
import com.example.kino.screen.common.typeenum.TypeEnum
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

private const val TOP_FIVE = 5
private const val TOP_MORE = 14
private const val TopFiveName = "Топ 5"
private const val TopVoteCount = "Топ по рейтингу"

class MovieViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val resultId: MutableSharedFlow<String> = MutableSharedFlow(0)
    val responseId: SharedFlow<String> get() = resultId

    private val resultGenresByPosition: MutableSharedFlow<Genres> = MutableSharedFlow(replay = 0)
    val responseGenresByPosition: SharedFlow<Genres> get() = resultGenresByPosition

    private val _movieByGenres: MutableSharedFlow<List<GenresList>> = MutableSharedFlow(0)
    val movieByGenres: SharedFlow<List<GenresList>> get() = _movieByGenres.asSharedFlow()


    init {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                getGenres()
            }
        }
    }

    fun getMovieClick(id: String) {
        viewModelScope.launch {
            resultId.emit(id)
        }
    }

    fun clickByCategory(category: GenresList) {
        viewModelScope.launch {
            if (category.type == -1) {
                when (category.name) {
                    TopFiveName -> {
                    }
                    TopVoteCount -> {
                    }
                    else -> {
                    }
                }
            } else {
                resultGenresByPosition.emit(Genres(idGenres = category.idGenres, name = category.name, type = ""))
            }
        }
    }

    private fun selectionTopMore(list: List<MovieResult>, countElement: Int): List<MovieResult> {
        val editList = list.toMutableList()
        editList.sortByDescending { it.voteCount }
        return editList.take(countElement).toMutableList()
    }

    private suspend fun getGenres() {
        val genres = databaseRepository.getGenres(TypeEnum.MOVIE)
        getMovieByIdGenres(genres)
    }

    private suspend fun getMovieByIdGenres(genres: List<Genres>) {
        val top: Movie = networkRepository.getPopularity(1)
        val rotate: Movie = networkRepository.getRotate(1)
        val genre: MutableList<GenresList> = mutableListOf()
        genres.forEach {
            val networckGenres = networkRepository.getFilm(1, it.idGenres.toString())
            val genresList =
                GenresList(0,
                    it.name,
                    it.idGenres,
                    selectionTopMore(networckGenres.result, TOP_MORE))
            genre.add(genresList)
        }

        val rotateList: List<MovieResult> = selectionTopMore(rotate.result, TOP_FIVE)
        val topList: List<MovieResult> = selectionTopMore(top.result, TOP_FIVE)
        genre.add(0, GenresList(-1, TopFiveName, -1, topList))
        val int = Random.nextInt(2, genre.size - 2)
        genre.add(int, GenresList(-1, TopVoteCount, -2, rotateList))
        _movieByGenres.emit(genre)
    }
}