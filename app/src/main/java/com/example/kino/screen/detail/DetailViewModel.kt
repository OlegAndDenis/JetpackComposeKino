package com.example.kino.screen.detail

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val resultMovie: MutableSharedFlow<MovieDetail> = MutableSharedFlow(0)
    val responseMovie: SharedFlow<MovieDetail> get() = resultMovie.asSharedFlow()

    private val _collections: MutableSharedFlow<List<MovieResult>> = MutableSharedFlow(0)
    val collections: SharedFlow<List<MovieResult>> get() = _collections.asSharedFlow()

    private val _overview: MutableSharedFlow<MovieDetail> = MutableSharedFlow(0)
    val overview: SharedFlow<MovieDetail> get() = _overview.asSharedFlow()

    private val _actor: MutableSharedFlow<Actors> = MutableSharedFlow(0)
    val actor: SharedFlow<Actors> get() = _actor.asSharedFlow()

    private val _mapFragment: MutableSharedFlow<Map<Fragment, String>> = MutableSharedFlow(0)
    val mapFragment: SharedFlow<Map<Fragment, String>> get() = _mapFragment.asSharedFlow()

    fun requestId(id: String) {
        viewModelScope.launch {
            val movie = networkRepository.getMovie(id)
            val actors = networkRepository.getActors(id)
            resultMovie.emit(movie)
            _overview.emit(movie)
            createMapFragment(Pair(movie, actors))
        }
    }

    suspend fun createMapFragment(pair: Pair<MovieDetail, Actors>) {
        val map = mutableMapOf<Fragment, String>()
        map[OverviewFragment()] = "описание"
        if (pair.second.cast.isNotEmpty()) {
            map[ActorsFragment()] = "актеры"
        }
        if (pair.first.productionCompanies.isNotEmpty()) {
            map[CompanyFragment()] = "издатели"
        }

        map[CollectionsFragment()] = "коллекция"
        _mapFragment.emit(map)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}