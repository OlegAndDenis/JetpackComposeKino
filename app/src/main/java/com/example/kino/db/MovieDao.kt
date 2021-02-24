package com.example.kino.db

import androidx.room.*
import com.example.kino.db.model.CheckColumnDB
import com.example.kino.db.model.Genres

@Dao
interface MovieDao {
    @Query("select count() > 0 as isNotEmpty from genres")
    fun isNotEmptyGenresAll(): CheckColumnDB

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: Genres)

    @Transaction
    fun insertAll(lestGenres: List<Genres>) {
        lestGenres.forEach {
            val genresGet = searchIdGenres(it.idGenres)
            if (genresGet != null) {
                updateGenres("${genresGet.type}, ${it.type}", it.idGenres)
            } else {
                insertGenres(it)
            }
        }
    }

    @Query("update genres set type = :type where idGenres =:id ")
    fun updateGenres(type: String, id : Int)

    @Query("select * from genres where idGenres == :id")
    fun searchIdGenres(id: Int): Genres

//    %tv%
    @Query("select * from genres where genres.type like :type")
    fun getAllGenresType(type: String) : List<Genres>
}