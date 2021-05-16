package com.example.kino.screen.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.SingleLiveEvent
import com.example.kino.db.DatabaseRepository
import com.example.kino.db.model.Genres
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.plusAssign
import com.example.kino.screen.GenresList
import com.example.kino.screen.common.TypeEnum
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import kotlin.random.Random

private const val ALL_MOVIE_COUNT = 13
private const val ZERO = 0
private const val TOP_FIVE = 5
private const val TOP_MORE = 7

class MovieViewModel(
    private val networkRepository: NetworkRepository,
    private val databaseRepository: DatabaseRepository,
) : ViewModel() {

    private val resultId: SingleLiveEvent<String> = SingleLiveEvent()
    val responseId: LiveData<String> = resultId

    private val resultGenresByPosition: MutableLiveData<Genres> = MutableLiveData()
    val responseGenresByPosition: LiveData<Genres> = resultGenresByPosition

    private val _movieByGenres: SingleLiveEvent<List<GenresList>> = SingleLiveEvent()
    val movieByGenres: LiveData<List<GenresList>> = _movieByGenres

    private val disposable = CompositeDisposable()

    init {
        getGenres()
    }

    fun getMovieClick(id: String) {
        resultId.postValue(id)
    }

    private fun selectTopFive(list: List<MovieResult>): List<MovieResult> {
        val editList = list.toMutableList()
        editList.sortByDescending { it.voteCount }
        val topFiv = editList.take(TOP_FIVE).toMutableList()
        val paths = addLastElement(list)
        topFiv.add(MovieResult(paths = paths))
        return topFiv
    }

    private fun selectionTopMore(list: List<MovieResult>): List<MovieResult> {
        val editList = list.toMutableList()
        editList.sortByDescending { it.voteCount }
        val topMore = editList.take(TOP_MORE).toMutableList()
        val paths = addLastElement(list)
        topMore.add(MovieResult(paths = paths))
        return topMore
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
        disposable += databaseRepository.getGenres(TypeEnum.MOVIE)
            .toObservable()
            .subscribeOn(Schedulers.io())
            .subscribe(this::getMovieByIdGenres, Timber::e)
    }

    private fun getMovieByIdGenres(genres: List<Genres>) {
        val top: Single<Movie> = networkRepository.getPopularity(1)
        val rotate: Single<Movie> = networkRepository.getRotate(1)
        val genre: MutableList<Single<GenresList>> = mutableListOf()
        genres.forEach {
            val single = networkRepository.getFilm(1, it.idGenres.toString())
                .map { movie ->
                    GenresList(0,
                        it.name,
                        it.idGenres,
                        selectionTopMore(movie.result))
                }
            genre.add(single)
        }

        disposable += Single.zip(genre, Function { anyList ->
            val list = mutableListOf<GenresList>()
            anyList.forEach {
                list.add(it as GenresList)
            }
            return@Function list
        }).subscribeOn(Schedulers.io())
            .flatMap { list ->
                Single.zip(rotate, top, ::Pair).map { Triple(it.first, it.second, list) }
            }
            .subscribe({
                val rotateList: List<MovieResult> = selectTopFive(it.first.result)
                val topList: List<MovieResult> = selectTopFive(it.second.result)
                it.third.add(0, GenresList(-1, "Топ 5", -1, topList))
                val int = Random.nextInt(2, it.third.size - 1)
                it.third.add(int, GenresList(-1, "Топ по рейтингу", -2, rotateList))
                _movieByGenres.postValue(it.third)
            }, Timber::e)
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}