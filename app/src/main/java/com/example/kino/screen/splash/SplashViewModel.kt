package com.example.kino.screen.splash

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import com.example.kino.applicationm.MovieApplication
import com.example.kino.di.components.ActivityComponent
import com.example.kino.di.moduls.ActivityModule
import com.example.kino.network.NetworkRepository


class SplashViewModel(
    application: Application,
    private val mNetworkRepository: NetworkRepository)
    : AndroidViewModel(application) {

    private val resultLiveData: MutableLiveData<String> = MutableLiveData()
    val attachObservable: LiveData<String> = resultLiveData

    private val START_RESULT: String = "INIT"

    init {
        resultLiveData.postValue(START_RESULT)
        startNetwork()
    }

    private fun startNetwork() {
        mNetworkRepository.isDownloadGenres(object :NetworkRepository.ResultSuccess{
            override fun setSuccess(result: String) {
                resultLiveData.postValue(result)
            }
        })
    }

    fun restart() {
        startNetwork()
    }
}