package com.example.kino.screen.serialfragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.db.DatabaseRepository
import com.example.kino.db.model.Genres
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.serial.Serials
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SerialViewModel(
    private val mNetworkRepository: NetworkRepository,
    private val mDatabaseRepository: DatabaseRepository
) : ViewModel() {

    private val TYPE = "tv"

    private val resultMovie: MutableLiveData<Serials> = MutableLiveData()
    val responseMovie: LiveData<Serials> = resultMovie

    private val resultGenres: MutableLiveData<List<Genres>> = MutableLiveData()
    val responseGenres: LiveData<List<Genres>> = resultGenres

    init {
        getPopulate(1)
        getGenres()
    }

    private fun getPopulate(page: Int) {
        mNetworkRepository.getSerials(page)
            .subscribeOn(Schedulers.io())
            .subscribe(resultMovie::postValue, Timber::e)
    }

    private fun getGenres() {
        mDatabaseRepository.getGenres("%$TYPE%")
            .subscribeOn(Schedulers.io())
            .subscribe(resultGenres::postValue, Timber::e)
    }
}