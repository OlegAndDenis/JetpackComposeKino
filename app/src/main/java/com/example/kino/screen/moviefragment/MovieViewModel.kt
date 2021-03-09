package com.example.kino.screen.moviefragment

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kino.db.DatabaseRepository
import com.example.kino.db.model.Genres
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.Movie
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class MovieViewModel(
    application: Application,
    private val mNetworkRepository: NetworkRepository,
    private val mDatabaseRepository: DatabaseRepository
) : AndroidViewModel(application) {

    private val resultMovie: MutableLiveData<Movie> = MutableLiveData()
    val responseMovie: LiveData<Movie> = resultMovie

    private val resultGenres: MutableLiveData<List<Genres>> = MutableLiveData()
    val responseGenres: LiveData<List<Genres>> = resultGenres

    init {
        getPopulate(1)
        getGenres()
    }

    private fun getPopulate(page: Int) {
        mNetworkRepository.getMovie(page)
            .subscribeOn(Schedulers.io())
            .subscribe(resultMovie::postValue, Timber::e)
    }

    private fun getGenres() {
        mDatabaseRepository.getGenres("%movie%")
            .subscribeOn(Schedulers.io())
            .subscribe(resultGenres::postValue, Timber::e)
    }
}