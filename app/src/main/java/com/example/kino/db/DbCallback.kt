package com.example.kino.db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class DbCallback : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
    }

    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
        super.onDestructiveMigration(db)
    }
}