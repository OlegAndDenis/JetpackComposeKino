package com.example.kino.screen.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelTransaction() : ViewModel() {

    private val resultLiveData: MutableLiveData<String> = MutableLiveData()
    val attachObservable: LiveData<String> = resultLiveData

   fun transaction(TAG: String) {
       resultLiveData.value = TAG
   }
}