package com.example.kino.screen.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.SingleLiveEvent
import com.example.kino.db.model.Genres
import com.example.kino.network.model.movie.MovieDetail

class TransactionViewModel : ViewModel() {

    private val resultId: SingleLiveEvent<String> = SingleLiveEvent()
    val responseId: LiveData<String> = resultId

    private val resultTop: SingleLiveEvent<Unit> = SingleLiveEvent()
    val responseTop: LiveData<Unit> = resultTop

    private val resultGenres: SingleLiveEvent<Genres> = SingleLiveEvent()
    val responseGenres: LiveData<Genres> = resultGenres

    private val _overView: SingleLiveEvent<MovieDetail> = SingleLiveEvent()
    val overView: LiveData<MovieDetail> = _overView

    private val _mapFragment: SingleLiveEvent<Map<Fragment, String>> = SingleLiveEvent()
    val mapFragment: LiveData<Map<Fragment, String>> = _mapFragment

    fun callId(id: String) {
        resultId.postValue(id)
    }

    fun callTop() {
        resultTop.postValue(Unit)
    }

    fun callGenres(genres: Genres) {
        resultGenres.postValue(genres)
    }

    fun callOverView(overview: MovieDetail) {
        _overView.postValue(overview)
    }

    fun callFragment(map: Map<Fragment, String>) {
        _mapFragment.postValue(map)
    }
}