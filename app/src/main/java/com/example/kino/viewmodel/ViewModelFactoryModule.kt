package com.example.kino.viewmodel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.example.kino.screen.splash.SplashViewModel
import com.example.kino.db.DatabaseRepository
import com.example.kino.di.scope.ApplicationScope
import com.example.kino.network.NetworkRepository
import com.example.kino.screen.moviefragment.MovieViewModel
import com.example.kino.screen.serialfragment.SerialViewModel
import com.example.kino.screen.screncontainer.ContainerViewModel
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
    fun provideViewModelFactory(providerMap: Map<Class<out ViewModel>,
            @JvmSuppressWildcards Provider<ViewModel>>,
    @NonNull application: Application): ViewModelFactory =
        ViewModelFactory(providerMap, application)

    @Provides
    @IntoMap
    @ViewModelKey(ContainerViewModel::class)
    fun provideContainerViewModel(@NonNull application: Application): ViewModel =
        ContainerViewModel(application)

    @Provides
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun provideSplashViewModel(@NonNull application: Application,
                               @NonNull networkRepository: NetworkRepository): ViewModel =
        SplashViewModel(application, networkRepository)

    @Provides
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    fun provideMovieViewModel(@NonNull application: Application,
                               @NonNull networkRepository: NetworkRepository,
    @NonNull databaseRepository: DatabaseRepository): ViewModel =
        MovieViewModel(application, networkRepository, databaseRepository)

    @Provides
    @IntoMap
    @ViewModelKey(SerialViewModel::class)
    fun provideSerialsViewModel(@NonNull application: Application,
                              @NonNull networkRepository: NetworkRepository,
                              @NonNull databaseRepository: DatabaseRepository): ViewModel =
        SerialViewModel(application, networkRepository, databaseRepository)
}