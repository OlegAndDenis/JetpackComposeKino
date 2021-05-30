package com.example.kino.screen.common

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kino.db.model.Genres
import com.example.kino.network.model.movie.MovieDetail
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class TransactionViewModel : ViewModel() {

    private val resultId: MutableStateFlow<String> = MutableStateFlow("")
    val responseId: StateFlow<String> get() = resultId.asStateFlow()

    private val resultTop: MutableStateFlow<Unit> = MutableStateFlow(Unit)
    val responseTop: StateFlow<Unit> get() = resultTop.asStateFlow()

    private val resultGenres: MutableStateFlow<Genres> = MutableStateFlow(Genres(0, 0, "",""))
    val responseGenres: StateFlow<Genres> get() = resultGenres.asStateFlow()

    private val _overView: MutableStateFlow<MovieDetail> = MutableStateFlow(MovieDetail())
    val overView: StateFlow<MovieDetail> get() = _overView.asStateFlow()

    private val _mapFragment: MutableStateFlow<Map<Fragment, String>> = MutableStateFlow(mapOf())
    val mapFragment: StateFlow<Map<Fragment, String>> get() = _mapFragment.asStateFlow()

    fun callId(id: String) {
        viewModelScope.launch {
            resultId.emit(id)
        }
    }

    fun callTop() {
        viewModelScope.launch {
            resultTop.emit(Unit)
        }
    }

    fun callGenres(genres: Genres) {
        viewModelScope.launch {
            resultGenres.emit(genres)
        }
    }

    fun callOverView(overview: MovieDetail) {
        viewModelScope.launch {
            _overView.emit(overview)
        }
    }

    fun callFragment(map: Map<Fragment, String>) {
        viewModelScope.launch {
            _mapFragment.emit(map)
        }
    }
}