package com.example.kino.screen.listmovieview.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.screen.movie.model.OldAndNewList
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllMovieViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val resultMove: MutableStateFlow<Movie> = MutableStateFlow(Movie(page = -99))
    private val genres: MutableStateFlow<String> = MutableStateFlow("")

    private val resultMoveResult: MutableStateFlow<OldAndNewList> = MutableStateFlow(OldAndNewList())
    val responseMovieResult: StateFlow<OldAndNewList> get() = resultMoveResult.asStateFlow()

    private val _resultId: MutableSharedFlow<String> = MutableSharedFlow(0)
    val resultId: SharedFlow<String> get() = _resultId.asSharedFlow()

    private val _title: MutableSharedFlow<String> = MutableSharedFlow(0)
    val title: SharedFlow<String> get() = _title.asSharedFlow()

    private val _titleHack: MutableSharedFlow<String> = MutableSharedFlow(1)
    val titleHack: SharedFlow<String> get() = _titleHack.asSharedFlow()


    fun setTitle(title: String) {
        viewModelScope.launch {
            _title.emit(title)
        }
    }

    fun setTitleForHack(title: String) {
        viewModelScope.launch {
            _titleHack.emit(title)
        }
    }

    fun getMovieClick(id: Long) {
        viewModelScope.launch {
            _resultId.emit(id.toString())
        }
    }

    fun setGenres(id: String) {
        genres.value = id
    }

    fun newPage() {
        if (resultMove.value.page != -99) {
            val page = resultMove.value.page.plus(1)
            if (page != -1 && page != resultMove.value.totalPages) {
                getMovieByGenres(page)
            }
        } else {
            getMovieByGenres(1)
        }
    }

    private fun getMovieByGenres(page: Int) {
        val value = genres.value
        val genresId: String = value
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val film = networkRepository.getFilm(page, genresId)
                resultMove.emit(film)
                addMovie(film.result)
            }
        }
    }

    private suspend fun addMovie(newMovies: List<MovieResult>) {
        if (responseMovieResult.value.old.isNotEmpty()) {
            val old = resultMoveResult.value.old.toMutableList()
            old.addAll(newMovies)
            resultMoveResult.value = OldAndNewList(old, newMovies)
        } else {
            resultMoveResult.value = OldAndNewList(new = newMovies, old = newMovies)
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}