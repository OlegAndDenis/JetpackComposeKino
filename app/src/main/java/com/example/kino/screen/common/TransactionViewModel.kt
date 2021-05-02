package com.example.kino.screen.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransactionViewModel: ViewModel() {

    private val resultId: MutableLiveData<String> = MutableLiveData()
    val responseId: LiveData<String> = resultId

    fun callId(id: String) {
        resultId.postValue(id)
    }
}