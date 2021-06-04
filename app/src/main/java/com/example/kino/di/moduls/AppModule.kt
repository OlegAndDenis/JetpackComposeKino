package com.example.kino.di.moduls

import android.app.Application
import android.content.Context
import com.example.kino.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val application: Application) {

    @Provides
    @ApplicationScope
    fun provideApplication(): Application = application

    @Provides
    @ApplicationScope
    fun provideContext(): Context = application
}