package com.example.kino.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory(private val creators: Map<Class<out ViewModel>,
        Provider<ViewModel>>,
private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator: Provider<out ViewModel?> = creators[modelClass]
            ?: throw RuntimeException("unsupported view model class: $modelClass")
        return try {
            (creator.get() as T)
        } catch (e: Exception) {
            Timber.e(e)
            (EmptyViewModel(application) as T)
        }
    }
}