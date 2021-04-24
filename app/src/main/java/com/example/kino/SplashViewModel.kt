package com.example.kino

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.network.NetworkRepository

class SplashViewModel(
    private val mNetworkRepository: NetworkRepository
) : ViewModel() {

    private val resultLiveData: MutableLiveData<String> = MutableLiveData()
    val attachObservable: LiveData<String> = resultLiveData

    private val START_RESULT: String = "INIT"

    init {
        resultLiveData.postValue(START_RESULT)
        startNetwork()
    }

    private fun startNetwork() {
        mNetworkRepository.isDownloadGenres(object : NetworkRepository.ResultSuccess {
            override fun setSuccess(result: String) {
                resultLiveData.postValue(result)
            }
        })
    }

    fun restart() {
        startNetwork()
    }
}