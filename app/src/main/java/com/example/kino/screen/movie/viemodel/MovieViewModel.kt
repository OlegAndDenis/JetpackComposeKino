package com.example.kino.screen.movie.viemodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.connectoninfo.model.ConnectionType
import com.example.kino.db.DatabaseRepository
import com.example.kino.db.model.Genres
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.screen.common.model.GenresList
import com.example.kino.screen.common.typeenum.TypeEnum
import com.example.kino.screen.movie.MovieUiState
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
    connectionInfo: StateFlow<ConnectionType>,
) : ViewModel() {

    private val resultId: MutableSharedFlow<String> =
        MutableSharedFlow(0)
    val responseId: SharedFlow<String> = resultId.asSharedFlow()

    private val resultGenresByPosition: MutableSharedFlow<Genres> =
        MutableSharedFlow(0)
    val responseGenresByPosition: SharedFlow<Genres> = resultGenresByPosition.asSharedFlow()

    private val _movieByGenres: MutableSharedFlow<List<GenresList>> =
        MutableSharedFlow(0)
    val movieByGenres: SharedFlow<List<GenresList>> = _movieByGenres.asSharedFlow()

    private val _uiNotification: MutableSharedFlow<MovieUiState> =
        MutableSharedFlow(0)
    val uiNotification: SharedFlow<MovieUiState> = _uiNotification.asSharedFlow()

    private val _loadNewData: MutableSharedFlow<Unit> = MutableSharedFlow(0)
    val loadNewData: SharedFlow<Unit> = _loadNewData.asSharedFlow()

    init {
        connectionInfo
            .onEach(::checkConnectionStateInfo)
            .launchIn(viewModelScope)
    }

    private suspend fun checkConnectionStateInfo(connectionType: ConnectionType) {
        when (connectionType) {
            is ConnectionType.Available -> {
                _uiNotification.emit(MovieUiState.ConnectionAvailable)
                _loadNewData.emit(Unit)
            }
            is ConnectionType.Lost -> _uiNotification.emit(MovieUiState.ConnectionLost)
            is ConnectionType.Init -> { }
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
                    TopFiveName -> { }
                    TopVoteCount -> { }
                    else -> { }
                }
            } else {
                resultGenresByPosition.emit(
                    Genres(0L,category.idGenres, category.name, "")
                )
            }
        }
    }

    fun downloadData() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val genres = databaseRepository.getGenres(TypeEnum.MOVIE)
                getMovieByIdGenres(genres)
            }
        }
    }

    private suspend fun getMovieByIdGenres(genres: List<Genres>) {
        val top: Movie = networkRepository.getPopularity(1)
        val rotate: Movie = networkRepository.getRotate(1)
        val genre: MutableList<GenresList> = mutableListOf()
        genres.forEach {
            val networkGenres = networkRepository.getFilm(1, it.idGenres.toString())
            val genresList =
                GenresList(0, it.name, it.idGenres,
                    selectionTopMore(networkGenres.result, TOP_MORE)
                )
            genre.add(genresList)
        }

        val rotateList: List<MovieResult> = selectionTopMore(rotate.result, TOP_FIVE)
        val topList: List<MovieResult> = selectionTopMore(top.result, TOP_FIVE)
        genre.add(0, GenresList(-1, TopFiveName, -1, topList))
        val int = Random.nextInt(2, genre.size - 2)
        genre.add(int, GenresList(-1, TopVoteCount, -2, rotateList))
        _movieByGenres.emit(genre)
    }

    private fun selectionTopMore(list: List<MovieResult>, countElement: Int): List<MovieResult> {
        val editList = list.toMutableList()
        editList.sortByDescending { it.voteCount }
        return editList.take(countElement).toMutableList()
    }
}