package com.example.kino.db.model

import androidx.room.ColumnInfo

data class CheckColumnDB(
    @ColumnInfo(name = "isNotEmpty")
    var isNotEmpty : Boolean)