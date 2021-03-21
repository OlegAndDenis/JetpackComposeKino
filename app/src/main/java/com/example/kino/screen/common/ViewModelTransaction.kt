package com.example.kino.screen.common

import android.app.Application

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ViewModelTransaction(application: Application) : AndroidViewModel(application) {

    private val resultLiveData: MutableLiveData<String> = MutableLiveData()
    val attachObservable: LiveData<String> = resultLiveData

   fun transaction(TAG: String) {
       resultLiveData.value = TAG
   }
}