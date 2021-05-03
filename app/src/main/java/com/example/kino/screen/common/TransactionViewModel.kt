package com.example.kino.screen.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.SingleLiveEvent
import com.example.kino.db.model.Genres

class TransactionViewModel : ViewModel() {

    private val resultId: SingleLiveEvent<String> = SingleLiveEvent()
    val responseId: LiveData<String> = resultId

    private val resultTop: SingleLiveEvent<Unit> = SingleLiveEvent()
    val responseTop: LiveData<Unit> = resultTop

    private val resultGenres: SingleLiveEvent<Genres> = SingleLiveEvent()
    val responseGenres: LiveData<Genres> = resultGenres

    fun callId(id: String) {
        resultId.postValue(id)
    }

    fun callTop() {
        resultTop.postValue(Unit)
    }

    fun callGenres(genres: Genres) {
        resultGenres.postValue(genres)
    }
}