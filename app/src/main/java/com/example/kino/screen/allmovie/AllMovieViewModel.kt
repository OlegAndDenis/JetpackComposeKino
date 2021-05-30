package com.example.kino.screen.allmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.screen.movie.OldAndNewList
import com.example.kino.SingleLiveEvent
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

    private val resultMoveResult: SingleLiveEvent<OldAndNewList> = SingleLiveEvent()
    val responseMovieResult: LiveData<OldAndNewList> get() = resultMoveResult

    private val resultId: SingleLiveEvent<String> = SingleLiveEvent()
    val responseId: LiveData<String> = resultId

    private val genres: MutableLiveData<String> = MutableLiveData()

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String> = _title


    fun setTitle(title: String) {
        _title.value = title
    }

    fun getMovieClick(id: Long) {
        resultId.postValue(id.toString())
    }

    fun setGenres(id: String) {
        genres.value = id
    }

    fun newPage() {
        if (resultMove.value != null) {
            val page = resultMove.value?.page?.plus(1) ?: -1
            if (page != -1 && page != resultMove.value?.totalPages) {
                getMovieByGenres(page)
            }
        } else {
            getMovieByGenres(1)
        }
    }

    private fun getMovieByGenres(page: Int) {
//        val value = genres.value
//        val genresId: String = if (value != null && value != "-1") value.toString() else ""
//        disposable += networkRepository.getFilm(page, genresId)
//            .subscribeOn(Schedulers.io())
//            .subscribe({
//                resultMove.postValue(it)
//                addMovie(it.result)
//            }, Timber::e)
    }

    private fun addMovie(newMovies: List<MovieResult>) {
        if (responseMovieResult.value != null) {
            val old = resultMoveResult.value?.old?.toMutableList()
            old?.addAll(newMovies)
            resultMoveResult.postValue(OldAndNewList(old!!, newMovies))
        } else {
            resultMoveResult.postValue(OldAndNewList(new = newMovies, old = newMovies))
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}