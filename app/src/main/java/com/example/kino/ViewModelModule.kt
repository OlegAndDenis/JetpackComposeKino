package com.example.kino

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkRepository
import com.example.kino.screen.common.TransactionViewModel
import com.example.kino.screen.movie.MovieViewModel
import com.example.kino.screen.splash.SplashViewModel
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import kotlin.reflect.KClass

@Module
class ViewModelModule {

    @Suppress("DEPRECATED_JAVA_ANNOTATION")
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @MapKey
    internal annotation class ViewModelKey(val value: KClass<out ViewModel>)
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(ContainerViewModel::class)
//    fun provideContainerViewModel(
//        @NonNull networkRepository: NetworkRepository
//    ): ViewModel =
//        ContainerViewModel(networkRepository)
//
    @Provides
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    fun provideSplashViewModel(
        @NonNull networkRepository: NetworkRepository
    ): ViewModel =
        SplashViewModel(networkRepository)

    @Provides
    @IntoMap
    @ViewModelKey(MovieViewModel::class)
    fun provideMovieViewModel(
        @NonNull networkRepository: NetworkRepository,
        @NonNull databaseRepository: DatabaseRepository
    ): ViewModel =
        MovieViewModel(networkRepository, databaseRepository)

    @Provides
    @IntoMap
    @ViewModelKey(TransactionViewModel::class)
    fun provideTransactionViewModel(): ViewModel =
       TransactionViewModel()

//    @Provides
//    @IntoMap
//    @ViewModelKey(SerialViewModel::class)
//    fun provideSerialsViewModel(
//        @NonNull networkRepository: NetworkRepository,
//        @NonNull databaseRepository: DatabaseRepository
//    ): ViewModel =
//        SerialViewModel(networkRepository, databaseRepository)
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(ViewModelTransaction::class)
//    fun provideViewModelTransaction(): ViewModel =
//        ViewModelTransaction()
}