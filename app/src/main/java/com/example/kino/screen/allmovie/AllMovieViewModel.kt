package com.example.kino.screen.allmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.OldAndNewList
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.plusAssign
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class AllMovieViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val disposable = CompositeDisposable()

    private val resultMove: MutableLiveData<Movie> = MutableLiveData()

    private val resultMoveResult: MutableLiveData<OldAndNewList> = MutableLiveData()
    val responseMovieResult: LiveData<OldAndNewList> get() = resultMoveResult

    fun newTopPage() {
        if (resultMove.value != null) {
            val page = resultMove.value?.page?.plus(1) ?: -1
            if (page != -1 && page != resultMove.value?.totalPages) {
                getPopularity(page)
            }
        } else {
            getPopularity(1)
        }
    }

    private fun getPopularity(page: Int) {
        disposable += networkRepository.getFilm(page)
            .subscribeOn(Schedulers.io())
            .subscribe({
                resultMove.postValue(it)
                addMovie(it.result)
            }, Timber::e)
    }

    private fun addMovie(newMovies: List<MovieResult>) {
        if (responseMovieResult.value != null) {
            val old = resultMoveResult.value?.old?.toMutableList()
            old?.addAll(newMovies)
            resultMoveResult.postValue(OldAndNewList(old!!, newMovies))
        } else {
            resultMoveResult.postValue(OldAndNewList(new = newMovies))
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}