package com.example.kino.screen.detail

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.OverviewFragment
import com.example.kino.SingleLiveEvent
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.Actors
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.plusAssign
import com.example.kino.screen.ActorsFragment
import com.example.kino.screen.CollectionsFragment
import com.example.kino.screen.CompanyFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class DetailViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val resultMovie: SingleLiveEvent<MovieDetail> = SingleLiveEvent()
    val responseMovie: LiveData<MovieDetail> = resultMovie

    private val _collections: SingleLiveEvent<List<MovieResult>> = SingleLiveEvent()
    val collections: LiveData<List<MovieResult>> = _collections

    private val _overview: SingleLiveEvent<MovieDetail> = SingleLiveEvent()
    val overview: LiveData<MovieDetail> = _overview

    private val _actor: SingleLiveEvent<Actors> = SingleLiveEvent()
    val actor: LiveData<Actors> = _actor

    private val _mapFragment: SingleLiveEvent<Map<Fragment, String>> = SingleLiveEvent()
    val mapFragment: LiveData<Map<Fragment, String>> = _mapFragment

    fun requestId(id: String) {
        disposable += networkRepository.getMovie(id)
            .toObservable()
            .flatMap(
                { movie -> networkRepository.getActors(movie.id.toString()).toObservable() },
                { movie, actor -> Pair(movie, actor) }
            )
            .subscribeOn(Schedulers.io())
            .subscribe({
                resultMovie.postValue(it.first)
                _overview.postValue(it.first)
                createMapFragment(it)
            }, Timber::e)
    }

    fun createMapFragment(pair: Pair<MovieDetail, Actors>) {
        val map = mutableMapOf<Fragment, String>()
        map[OverviewFragment()] = "описание"
        if (pair.second.cast.isNotEmpty()) {
            map[ActorsFragment()] = "актеры"
        }
        if (pair.first.productionCompanies.isNotEmpty()) {
            map[CompanyFragment()] = "издатели"
        }
        if (pair.first.belongsToCollections != null) {
            map[CollectionsFragment()] = "коллекция"
        }
        _mapFragment.postValue(map)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}