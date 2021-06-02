package com.example.kino.db.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "genres",
    indices = [Index(name = "id_unique", value = ["idGenres"], unique = true)]
)
data class Genres(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "idGenres") val idGenres: Int = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "type") var type: String = ""
): Parcelable