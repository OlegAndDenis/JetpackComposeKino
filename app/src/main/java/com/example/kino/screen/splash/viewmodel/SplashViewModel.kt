package com.example.kino.screen.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.network.NetworkEnum
import com.example.kino.network.NetworkRepository
import com.example.kino.network.NetworkRepository.ResultSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(
    private val mNetworkRepository: NetworkRepository,
) : ViewModel() {

    private val resultLiveData: MutableSharedFlow<NetworkEnum> = MutableSharedFlow(0)
    val attachObservable: SharedFlow<NetworkEnum> get() = resultLiveData.asSharedFlow()

    init {
        startNetwork()
    }

    private fun startNetwork() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mNetworkRepository.isDownloadGenres(object : ResultSuccess {
                    override fun success(result: NetworkEnum) {
                        viewModelScope.launch {
                            resultLiveData.emit(result)
                        }
                    }
                })
            }
        }
    }

    fun restart() {
        startNetwork()
    }
}