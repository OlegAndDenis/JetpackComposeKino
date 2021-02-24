package com.example.kino.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.kino.SplashViewModel
import com.example.kino.di.scope.ApplicationScope
import com.example.kino.screncontainer.ContainerViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import javax.inject.Provider
import kotlin.reflect.KClass

@Module
class ViewModelFactoryModule {

    @Suppress("DEPRECATED_JAVA_ANNOTATION")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

    @Provides
    @ApplicationScope
    fun provideViewModelFactory(providerMap: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>): ViewModelFactory =
        ViewModelFactory(providerMap)

    @Provides
    @IntoMap
    @ViewModelKey(ContainerViewModel::class)
    fun provideContainerViewModel(application: Application): ViewModel =
        ContainerViewModel(application)

    @Provides
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun provideSplashViewModel(application: Application): ViewModel =
        SplashViewModel(application)
}