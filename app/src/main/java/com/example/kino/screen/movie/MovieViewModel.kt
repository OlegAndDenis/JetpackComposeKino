package com.example.kino.screen.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.db.DatabaseRepository
import com.example.kino.db.model.Genres
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.plusAssign
import com.example.kino.screen.common.TypeEnum
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class MovieViewModel(
    private val mNetworkRepository: NetworkRepository,
    private val mDatabaseRepository: DatabaseRepository,
) : ViewModel() {

    private val resultMovie: MutableLiveData<List<MovieResult>> = MutableLiveData()
    val responseMovie: LiveData<List<MovieResult>> = resultMovie

    private val resultGenres: MutableLiveData<List<Genres>> = MutableLiveData()
    val responseGenres: LiveData<List<Genres>> = resultGenres

    private val disposable = CompositeDisposable()

    init {
        getPopulate(1)
        getGenres()
    }

    private fun getPopulate(page: Int) {
        disposable += mNetworkRepository.getFilm(page)
            .subscribeOn(Schedulers.io())
            .map {
                val list = it.result.toMutableList()
                list.sortByDescending { it.voteCount }
                val topFiv = list.take(5).toMutableList()
                Collections.rotate(topFiv, -3)
                topFiv
            }
            .subscribe(resultMovie::postValue, Timber::e)
    }

    private fun getGenres() {
        disposable += mDatabaseRepository.getGenres(TypeEnum.MOVIE)
            .subscribeOn(Schedulers.io())
            .subscribe(resultGenres::postValue, Timber::e)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}