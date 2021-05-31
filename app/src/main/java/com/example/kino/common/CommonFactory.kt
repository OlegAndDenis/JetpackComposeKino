package com.example.kino.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kino.applicationm.MovieApplication
import javax.inject.Provider

object CommonFactory: ViewModelProvider.Factory {

    private val injector = MovieApplication.instance?.commonAppComponent?.getViewModelProviders()

    private val providers: Map<Class<out ViewModel>, Provider<ViewModel>>
    get() = injector!!

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        requireNotNull(providers[modelClass]).get() as T
}