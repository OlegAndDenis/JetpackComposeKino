package com.example.ui_movie.movie

import androidx.lifecycle.viewModelScope
import com.example.base.network.base.BaseViewModel
import com.example.base.network.model.ConnectionType
import com.example.themdb_api.common.MovieResult
import com.example.themdb_api.movie.UiMovie
import com.example.themdb_api.themdbrepository.ThemdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val TOP_MORE = 14

@HiltViewModel
class MovieViewModel @Inject constructor(
    network: StateFlow<ConnectionType>,
    private val themdbRepository: ThemdbRepository
) : BaseViewModel<Event, MovieState, Effect>() {

    override fun setInitialState(): MovieState = MovieState.Init

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.CategorySelection -> {
                setEffect { Effect.Navigation.ToCategoryDetails(event.id) }
            }
        }
    }

    init {
        viewModelScope.launch {
            network
                .onEach {
                    when (it) {
                        is ConnectionType.Available -> {
                            Timber.i("Connection type Available")
                        }
                        is ConnectionType.Lost -> {
                            setState { MovieState.Loading }
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
        setState {  MovieState.Loading }
        viewModelScope.launch {
            val genre = themdbRepository.getListGenresByMovie()
            val popularity = themdbRepository.getPopularityMove()
            val topPopularity = selectionTopMore(popularity.result, 5)
            val listUiMovie = mutableListOf<UiMovie>()
            genre.genres.forEach {
                val movieApi = themdbRepository.getMovieByGenres(it.id.toString())
                val topList = selectionTopMore(movieApi.result, TOP_MORE)
                val uiMovie = UiMovie(it.name, it.id ,topList)
                listUiMovie.add(uiMovie)
            }
            if (listUiMovie.isNotEmpty()) {
                listUiMovie.add(0,  UiMovie("Top", movies = topPopularity))
                setState { MovieState.Result(listUiMovie) }
            } else {
                setState {  MovieState.ConnectionLost }
            }
            setEffect { Effect.DataWasLoaded }
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