package com.example.kino

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import com.example.kino.applicationm.MovieApplication
import com.example.kino.di.components.ActivityComponent
import com.example.kino.di.moduls.ActivityModule
import com.example.kino.network.NetworkRepository


class SplashViewModel(application: Application, val mNetworkRepository: NetworkRepository) : AndroidViewModel(application) {

    private val result: MutableLiveData<String> = MutableLiveData()

    private val START_RESULT: String = "START"
    private val NO_CONNECT_RESULT: String = "noConnect"

    init {
        result.value = START_RESULT
    }

    private fun startNetwork() {

    }

    fun attachObservable(): LiveData<String> = result
}