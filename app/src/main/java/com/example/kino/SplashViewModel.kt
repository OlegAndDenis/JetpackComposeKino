package com.example.kino

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager.*
import com.example.kino.worckmanager.WorkManager

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var oneTimeWorkRequest: OneTimeWorkRequest

    fun worker() : LiveData<WorkInfo>{
        oneTimeWorkRequest = OneTimeWorkRequest.Builder(WorkManager::class.java)
            .build()
        getInstance().enqueue(oneTimeWorkRequest)
        return getInstance().getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
    }

    fun cancelWorker() {
        getInstance().cancelWorkById(oneTimeWorkRequest.id)
    }
}