package com.example.kino.screen.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.db.DatabaseRepository
import com.example.kino.db.model.Genres
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.screen.GenresList
import com.example.kino.screen.common.TypeEnum
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlin.random.Random

private const val ALL_MOVIE_COUNT = 13
private const val ZERO = 0
private const val TOP_FIVE = 5
private const val TOP_MORE = 7

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

    private val disposable = CompositeDisposable()

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

    private fun selectTopFive(list: List<MovieResult>): List<MovieResult> {
        val editList = list.toMutableList()
        editList.sortByDescending { it.voteCount }
        val topFiv = editList.take(TOP_FIVE).toMutableList()
        val paths = addLastElement(list)
        topFiv.add(MovieResult(paths = paths))
        return topFiv
    }

    private fun selectionTopMore(list: List<MovieResult>): List<MovieResult> {
        val editList = list.toMutableList()
        editList.sortByDescending { it.voteCount }
        val topMore = editList.take(TOP_MORE).toMutableList()
        val paths = addLastElement(list)
        topMore.add(MovieResult(paths = paths))
        return topMore
    }

    private fun addLastElement(list: List<MovieResult>): List<String> {
        val paths = mutableListOf<String>()
        val randomCount = List(ALL_MOVIE_COUNT) { Random.nextInt(ZERO, list.size) }
        repeat(randomCount.size) {
            val path = createRandomPath(list, it)
            paths.add(path)
        }
        return paths
    }

    private fun createRandomPath(list: List<MovieResult>, index: Int): String = when {
        list[index].posterPath != "no" -> list[index].posterPath
        list[index].backdropPath != "no" -> list[index].backdropPath
        else -> "file:///android_asset/img/ic_launcher_round.png"
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
                GenresList(0, it.name, it.idGenres, selectionTopMore(networckGenres.result))
            genre.add(genresList)
        }

        val rotateList: List<MovieResult> = selectTopFive(rotate.result)
        val topList: List<MovieResult> = selectTopFive(top.result)
        genre.add(0, GenresList(-1, "Топ 5", -1, topList))
        val int = Random.nextInt(2, genre.size - 2)
        genre.add(int, GenresList(-1, "Топ по рейтингу", -2, rotateList))
        _movieByGenres.emit(genre)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}