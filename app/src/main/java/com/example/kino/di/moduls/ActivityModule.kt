package com.example.kino.di.moduls

import android.app.Activity
import com.example.kino.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: Activity) {

    @Provides
    @ActivityScope
    fun provideActivity(): Activity = activity
}