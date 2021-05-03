package com.example.kino.screen.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.SingleLiveEvent

class TransactionViewModel : ViewModel() {

    private val resultId: SingleLiveEvent<String> = SingleLiveEvent()
    val responseId: LiveData<String> = resultId

    private val resultTop: SingleLiveEvent<Unit> = SingleLiveEvent()
    val responseTop: LiveData<Unit> = resultTop

    private val resultGenres: SingleLiveEvent<Unit> = SingleLiveEvent()
    val responseGenres: LiveData<Unit> = resultGenres

    fun callId(id: String) {
        resultId.postValue(id)
    }

    fun callTop() {
        resultTop.postValue(Unit)
    }

    fun callGenres() {
        resultGenres.postValue(Unit)
    }
}