package com.example.kino.di.components

import com.example.kino.db.migration.MigrationModule
import com.example.kino.db.module.DataBaseModule
import com.example.kino.di.moduls.ActivityModule
import com.example.kino.di.moduls.AppModule
import com.example.kino.di.scope.ApplicationScope
import com.example.kino.network.NetworkModule
import com.example.kino.viewmodel.ViewModelFactoryModule
import dagger.Component

@ApplicationScope
@Component(
    modules = [AppModule::class,
        ViewModelFactoryModule::class,
        NetworkModule::class,
        DataBaseModule::class,
        MigrationModule::class]
)
interface AppComponent {

    fun getActivityComponent(activityModule: ActivityModule): ActivityComponent
}