package com.example.ui_tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.network.model.ConnectionType
import com.example.themdb_api.serials.SerialResult
import com.example.themdb_api.serials.UiSerial
import com.example.themdb_api.themdbrepository.ThemdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

private const val TOP_MORE = 14
private const val TOP_FIVE = 5

@HiltViewModel
class SerialViewModule @Inject constructor(
    network: StateFlow<ConnectionType>,
    private val themdbRepository: ThemdbRepository
) : ViewModel() {

    private val _serials: MutableSharedFlow<SerialState> = MutableSharedFlow()
    val serials: SharedFlow<SerialState> = _serials.asSharedFlow()

    init {
        viewModelScope.launch {
            network
                .onEach {
                    when (it) {
                        is ConnectionType.Available -> {
                        }
                        is ConnectionType.Lost -> _serials.emit(SerialState.ConnectionLost)
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
            val genre = themdbRepository.getListGenresBySerial()
            val popularity = themdbRepository.getPopularSerial()
            val topPopularity = selectionTopMore(popularity.result, TOP_FIVE)
            val listUiSerial = mutableListOf<UiSerial>()

            genre.genres.forEach {
                val serialApi = themdbRepository.getSerials(it.id.toString())
                val topList = selectionTopMore(serialApi.result, TOP_MORE)
                val uiMovie = UiSerial(it.name, it.id, topList)
                listUiSerial.add(uiMovie)
            }
            if (listUiSerial.isNotEmpty()) {
                _serials.emit(
                    SerialState.Result(
                        listUiSerial,
                        UiSerial("Top", serials = topPopularity)
                    )
                )
            } else {
                _serials.emit(SerialState.ConnectionLost)
            }
        }
    }

    private fun selectionTopMore(list: List<SerialResult>, countElement: Int): List<SerialResult> {
        val editList = list.toMutableList()
        editList.sortByDescending { it.voteCount }
        return editList.take(countElement).toMutableList()
    }
}