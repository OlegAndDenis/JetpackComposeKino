package com.example.kino.screen.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionViewModel : ViewModel() {

    private val resultId: MutableLiveData<String> = MutableLiveData()
    val responseId: LiveData<String> = resultId

    private val resultTop: MutableLiveData<Unit> = MutableLiveData()
    val responseTop: LiveData<Unit> = resultTop

    fun callId(id: String) {
        resultId.postValue(id)
    }

    fun callTop() {
        resultTop.postValue(Unit)
    }

    fun callGenres() {

    }
}