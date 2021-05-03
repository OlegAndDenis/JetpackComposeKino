package com.example.kino.screen.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.db.DatabaseRepository
import com.example.kino.db.model.Genres
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.plusAssign
import com.example.kino.screen.common.TypeEnum
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import kotlin.random.Random

private const val ALL_MOVIE_COUNT = 13
private const val ZERO = 0
private const val TOP_FIVE = 5
private const val SHIFT_LIST = -3

class MovieViewModel(
    private val mNetworkRepository: NetworkRepository,
    private val mDatabaseRepository: DatabaseRepository,
) : ViewModel() {

    private val resultMovie: MutableLiveData<List<MovieResult>> = MutableLiveData()
    val responseMovie: LiveData<List<MovieResult>> = resultMovie

    private val resultGenres: MutableLiveData<List<Genres>> = MutableLiveData()
    val responseGenres: LiveData<List<Genres>> = resultGenres

    private val resultId: MutableLiveData<String> = MutableLiveData()
    val responseId: LiveData<String> = resultId

    private val resultGenresByPosition: MutableLiveData<Genres> = MutableLiveData()
    val responseGenresByPosition: LiveData<Genres> = resultGenresByPosition

    private val disposable = CompositeDisposable()

    init {
        getPopulate(1)
        getGenres()
    }

    fun getMovieClick(position: Int) {
        val movieId: String = resultMovie.value?.get(position)?.id.toString()
        resultId.postValue(movieId)
    }

    fun getGenresByPosition(position: Int) {
        val genres: Genres? = resultGenres.value?.get(position)
        if (genres != null) {
            resultGenresByPosition.postValue(genres)
        }
    }

    private fun getPopulate(page: Int) {
        disposable += mNetworkRepository.getFilm(page, "")
            .subscribeOn(Schedulers.io())
            .map { selectedTopFive(it.result) }
            .subscribe(resultMovie::postValue, Timber::e)
    }

    private fun selectedTopFive(list: List<MovieResult>): List<MovieResult> {
        val editList = list.toMutableList()
        editList.sortByDescending { it.voteCount }
        val topFiv = editList.take(TOP_FIVE).toMutableList()
        Collections.rotate(topFiv, SHIFT_LIST)
        val paths = addLastElement(list)
        topFiv.add(MovieResult(paths = paths))
        return topFiv
    }

    private fun addLastElement(list: List<MovieResult>): List<String> {
        val paths = mutableListOf<String>()
        val randomCount = List(ALL_MOVIE_COUNT) { Random.nextInt(ZERO, list.size) }
        repeat(randomCount.size) {
            val path = createRandomPath(list, it)
            paths.add(path)
        }
        return paths
    }

    private fun createRandomPath(list: List<MovieResult>, index: Int): String = when {
        list[index].posterPath != "no" -> list[index].posterPath
        list[index].backdropPath != "no" -> list[index].backdropPath
        else -> "file:///android_asset/img/ic_launcher_round.png"
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