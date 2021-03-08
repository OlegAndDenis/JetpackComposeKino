package com.example.kino.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import com.example.kino.applicationm.MovieApplication
import com.example.kino.di.components.ActivityComponent
import com.example.kino.di.moduls.ActivityModule
import com.example.kino.network.NetworkRepository


class EmptyViewModel(
    application: Application)
    : AndroidViewModel(application) {
}