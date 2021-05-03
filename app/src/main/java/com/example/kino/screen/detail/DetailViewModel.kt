package com.example.kino.screen.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.plusAssign
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DetailViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val resultMovie: MutableLiveData<MovieResult> = MutableLiveData()
    val responseMovie: LiveData<MovieResult> = resultMovie

    fun requestId(id: String) {
        disposable += networkRepository.getMovie(id)
            .subscribeOn(Schedulers.io())
            .subscribe(resultMovie::postValue, Timber::e)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}