package com.example.kino.db

import com.example.kino.db.model.CheckColumnDB
import com.example.kino.network.model.GenresApi

interface DatabaseRepository {

    fun isNotEmptyGenresAll() : CheckColumnDB

    fun insertGenres(listGenres : List<GenresApi>, type : String)
}