package com.example.kino.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_1_2 : Migration(1,2) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("create unique index `index_genres_idGenres` on `genres` (`idGenres`)")
    }
}