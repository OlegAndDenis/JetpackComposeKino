package com.example.kino.di.components

import androidx.lifecycle.ViewModel
import com.example.kino.db.migration.MigrationModule
import com.example.kino.db.module.DataBaseModule
import com.example.kino.di.moduls.AppModule
import com.example.kino.di.scope.ApplicationScope
import com.example.kino.network.NetworkModule
import com.example.kino.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Provider

@ApplicationScope
@Component(
    modules = [
        AppModule::class,
        ViewModelModule::class,
        NetworkModule::class,
        DataBaseModule::class,
        MigrationModule::class]
)
interface AppComponent {

    fun getViewModelProviders(): Map<Class<out ViewModel>, Provider<ViewModel>>
}