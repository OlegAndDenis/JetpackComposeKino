package com.example.kino.db.module

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import com.example.kino.db.*
import com.example.kino.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class DataBaseModule {

    private val DB_NAME = "db_Movie.db"

    @Provides
    @ApplicationScope
    fun provideDatabaseRepository(dataBase: DataBase): DatabaseRepository {
        return DatabaseRepositoryImpl(dataBase)
    }

    @Provides
    fun provideDbCallback(): DbCallback {
        return DbCallback()
    }

    @Provides
    @ApplicationScope
    fun provideRomDatabase(application: Application, dbCallback: DbCallback): DataBase {
        return Room.databaseBuilder(application, DataBase::class.java, DB_NAME)
            .addCallback(dbCallback)
            .addMigrations()
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideMovieDAO(database: DataBase): MovieDao {
        return database.movieDao()
    }
}