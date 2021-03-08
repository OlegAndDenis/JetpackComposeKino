package com.example.kino.screen.moviefragment

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import com.example.kino.applicationm.MovieApplication
import com.example.kino.db.DatabaseRepository
import com.example.kino.di.components.ActivityComponent
import com.example.kino.di.moduls.ActivityModule
import com.example.kino.network.NetworkRepository


class MovieViewModel(
    application: Application,
    private val mNetworkRepository: NetworkRepository,
private val mDatabaseRepository: DatabaseRepository)
    : AndroidViewModel(application) {

}