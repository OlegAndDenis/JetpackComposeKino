package com.example.kino.screen.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.connectoninfo.model.ConnectionType
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkEnum
import com.example.kino.network.NetworkEnum.*
import com.example.kino.network.NetworkRepository
import com.example.kino.screen.common.typeenum.TypeEnum
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import timber.log.Timber
import java.lang.Exception

class SplashViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
    private val connectionInfo: StateFlow<ConnectionType>,
) : ViewModel() {

    private val _notificationUi: MutableSharedFlow<NetworkEnum> = MutableSharedFlow(0)
    val notificationUi: SharedFlow<NetworkEnum> get() = _notificationUi.asSharedFlow()

    private var job: Job? = null

    init {
        startNetwork()
    }

    private fun startNetwork() {
        job = viewModelScope.launch {
            connectionInfo
                .onEach {
                    Timber.i("$it")
                    when (it) {
                        is ConnectionType.Available -> selectGenresToDb()
                        is ConnectionType.Lost -> checkDbForData()
                        is ConnectionType.Init -> {
                        }
                        else -> _notificationUi.emit(ERROR)
                    }
                }.catch {
                    Timber.i("$it")
                    _notificationUi.emit(ERROR)
                }
                .launchIn(this)
        }
    }

    private fun checkDbForData() {
        viewModelScope.launch {
            val checkDB = try {
                databaseRepository.isNotEmptyGenresAll()
            } catch (e: Exception) {
                _notificationUi.emit(ERROR)
                return@launch
            }
            if (checkDB.isNotEmpty) {
                _notificationUi.emit(OK)
            } else {
                _notificationUi.emit(NO_CONNECTION)
            }
        }
        job?.cancel()
    }

    private fun selectGenresToDb() {
        viewModelScope.launch {
            val result = networkRepository.downloadGenres()
            if (result.first.genres.isEmpty() || result.second.genres.isEmpty()) {
                _notificationUi.emit(ERROR)
                return@launch
            }
            databaseRepository.insertGenres(result.first.genres, TypeEnum.MOVIE)
            databaseRepository.insertGenres(result.second.genres, TypeEnum.TV)
            _notificationUi.emit(OK)
        }
        job?.cancel()
    }

    fun restart() {
        startNetwork()
    }
}