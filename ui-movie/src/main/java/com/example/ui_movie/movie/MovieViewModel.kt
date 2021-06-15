package com.example.ui_movie.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.network.model.ConnectionType
import com.example.themdb_api.MovieResult
import com.example.themdb_api.UiMovie
import com.example.themdb_api.themdbrepository.ThemdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val TOP_MORE = 14

@HiltViewModel
class MovieViewModel @Inject constructor(
    network: StateFlow<ConnectionType>,
    private val themdbRepository: ThemdbRepository
) : ViewModel() {

    private val _movieState = MutableStateFlow<MovieState>(MovieState.Loading())
    val movieState: StateFlow<MovieState> = _movieState.asStateFlow()

    init {
        _movieState.value = MovieState.Loading()
        viewModelScope.launch {
            network
                .onEach {
                    when (it) {
                        is ConnectionType.Available -> {
                            loadGenres()
                            Timber.i("Connection type Available")
                        }
                        is ConnectionType.Lost -> {
                            _movieState.value = MovieState.Loading()
                            Timber.i("Connection type lost")
                        }
                        else -> {
                            Timber.i("Connection type Init")
                        }
                    }
                }
                .launchIn(this)
        }
    }

    fun loadGenres() {
        viewModelScope.launch {
            val genre = themdbRepository.getListGenresByMovie()
            val popularity = themdbRepository.getPopularityMove()
            val topPopularity = selectionTopMore(popularity.result, 5)
            val listUiMovie = mutableListOf<UiMovie>()
            genre.genres.forEach {
                val movieApi = themdbRepository.getMovieByGenres(it.id.toString())
                val topList = selectionTopMore(movieApi.result, TOP_MORE)
                val uiMovie = UiMovie(it.name, topList)
                listUiMovie.add(uiMovie)
            }
            if (listUiMovie.isNotEmpty()) {
                _movieState.value = MovieState.Result(listUiMovie, UiMovie("Top", topPopularity))
            } else {
                _movieState.value = MovieState.ConnectionLost
            }
        }
    }

    private fun selectionTopMore(list: List<MovieResult>, countElement: Int): List<MovieResult> {
        val editList = list.toMutableList()
        editList.sortByDescending { it.voteCount }
        return editList.take(countElement).toMutableList()
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("Clear")
    }
}