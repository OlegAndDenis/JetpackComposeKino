package com.example.kino.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genres")
data class Genres(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "idGenres") val idGenres: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") var type: String
)