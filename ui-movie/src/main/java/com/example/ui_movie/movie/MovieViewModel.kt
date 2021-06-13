package com.example.ui_movie.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.network.model.ConnectionType
import com.example.themdb_api.themdbrepository.ThemdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    network: StateFlow<ConnectionType>,
    private val themdbRepository: ThemdbRepository
): ViewModel() {

    init {
        viewModelScope.launch {
            network
                .onEach {
                    when(it) {
                        is ConnectionType.Available -> {
                            themdbRepository.getListGenresByMovie()
                            Timber.i("Connection type Available")
                        }
                        is ConnectionType.Lost -> {
                            Timber.i("Connection type lost")
                        }
                        else -> {
                            Timber.i("Connection type Init")
                        }
                    }
                }
                .catch { }
                .launchIn(this)
        }
    }
}