package com.example.kino.worckmanager

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.kino.applicationm.MovieApplication
import com.example.kino.network.NetworkRepository
import javax.inject.Inject

class WorkManager(var context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    @Inject
    lateinit var mNetRepository : NetworkRepository

    private val  WORK_RESULT = "work_result"

    override fun doWork(): Result {
        (applicationContext as MovieApplication).commonAppComponent.inject(this)
        return if (connectionOnline()) {
            downloadGenres()
        } else {
            if (mNetRepository.isNotEmptyDB()) {
                Result.success(Data.Builder().putString(WORK_RESULT, "noConnect").build())
            } else {
                Result.success(Data.Builder().putString(WORK_RESULT, "finish").build())
            }
        }
    }

    private fun downloadGenres() : Result {
        return mNetRepository.isDownloadGenres()
    }

    private fun connectionOnline(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetworkInfo: NetworkInfo? = null
        activeNetworkInfo = cm.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
    }

    override fun onStopped() {
        super.onStopped()
    }
}