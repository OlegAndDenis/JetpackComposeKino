package com.example.kino.screen.moviefragment

import android.annotation.SuppressLint
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
import com.example.kino.network.model.Movie
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class MovieViewModel(
    application: Application,
    private val mNetworkRepository: NetworkRepository,
private val mDatabaseRepository: DatabaseRepository)
    : AndroidViewModel(application) {

    private val resultLiveData: MutableLiveData<Movie> = MutableLiveData()
    val responseMovie: LiveData<Movie> = resultLiveData

    init {
        getPopulate(1)
    }

    private fun getPopulate(page: Int) {
        mNetworkRepository.getMovie(page)
            .subscribeOn(Schedulers.io())
            .subscribe(resultLiveData::postValue, Timber::e)
    }
}