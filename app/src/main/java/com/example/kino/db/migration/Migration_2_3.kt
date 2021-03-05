package com.example.kino.db.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class Migration_2_3 : Migration(2,3) {

    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("drop table `genres`")
        database.execSQL("create table `genres` ( " +
                "`id`       INTEGER " +
                "PRIMARY KEY AUTOINCREMENT NOT NULL DEFAULT 0, " +
                "`idGenres` INTEGER, " +
                "`name`     TEXT, " +
                "`type`     TEXT)")
        database.execSQL("create unique index `id_unique` on `genres` (`idGenres`)")
    }
}