package com.example.kino.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kino.db.model.Genres

@Database(entities = [Genres::class], version = 1, exportSchema = false)
abstract class DataBase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}
