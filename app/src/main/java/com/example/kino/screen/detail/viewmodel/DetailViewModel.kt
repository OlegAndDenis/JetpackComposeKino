package com.example.kino.screen.detail.viewmodel

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.screen.pager.overview.OverviewFragment
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.Actors
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.screen.actors.ActorsFragment
import com.example.kino.screen.pager.collectionscreen.CollectionsFragment
import com.example.kino.screen.pager.companyscreen.CompanyFragment
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class DetailViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val resultMovie: MutableSharedFlow<MovieDetail> = MutableSharedFlow(0)
    val responseMovie: SharedFlow<MovieDetail> get() = resultMovie.asSharedFlow()

    private val _mapFragment: MutableSharedFlow<Map<Fragment, String>> = MutableSharedFlow(0)
    val mapFragment: SharedFlow<Map<Fragment, String>> get() = _mapFragment.asSharedFlow()

    fun requestId(id: String) {
        viewModelScope.launch {
            val movie = networkRepository.getMovie(id)
            val actors = networkRepository.getActors(id)
            resultMovie.emit(movie)
            createMapFragment(Pair(movie, actors))
        }
    }

    suspend fun createMapFragment(pair: Pair<MovieDetail, Actors>) {
        val map = mutableMapOf<Fragment, String>()
        map[OverviewFragment.newInstance(pair.first)] = "описание"

        if (pair.second.cast.isNotEmpty()) {
            map[ActorsFragment()] = "актеры"
        }
        if (pair.first.productionCompanies.isNotEmpty()) {
            map[CompanyFragment.newInstance(pair.first.productionCompanies)] = "издатели"
        }

        if (pair.first.belongsToCollections != null && !pair.first.belongsToCollections.isEmpty) {
            map[CollectionsFragment.newInstance(pair.first.belongsToCollections)] = "коллекция"
        }

        _mapFragment.emit(map)
    }

    override fun onCleared() {
        super.onCleared()
        Timber.i("onCleared")
    }
}