package com.example.kino.applicationm

import android.app.Application
import android.content.Context
import com.example.kino.di.components.AppComponent
import com.example.kino.di.components.DaggerAppComponent
import com.example.kino.di.moduls.AppModule
import timber.log.Timber
import java.util.*

class MovieApplication : Application() {

    lateinit var commonAppComponent : AppComponent

    init {
        instance = this@MovieApplication
        language = language()
    }

    companion object {
        private var instance: MovieApplication? = null
        var language : String = ""
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        commonAppComponent = initDagger(this@MovieApplication)
    }

    private fun initDagger(application: Application) : AppComponent =
        DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .build()

    private fun language() : String {
       return when(Locale.getDefault().language) {
            "ru" -> "ru-RU"
            "en" -> "en-US"
           else -> "ru-RU"
       }
    }
}