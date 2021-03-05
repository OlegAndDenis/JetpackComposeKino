package com.example.kino.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "genres",
    primaryKeys = ["id"],
    indices = [Index(value = ["idGenres"], unique = true)]
)
data class Genres(
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "idGenres") val idGenres: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") var type: String
)