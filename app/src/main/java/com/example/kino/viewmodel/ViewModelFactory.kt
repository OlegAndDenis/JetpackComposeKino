package com.example.kino.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider
import kotlin.reflect.KClass

class ViewModelFactory(private val creators: Map<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val creator: Provider<out ViewModel?> = creators[modelClass]
            ?: throw RuntimeException("unsupported view model class: $modelClass")
        try {
           return (creator.get() as T?)!!
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}