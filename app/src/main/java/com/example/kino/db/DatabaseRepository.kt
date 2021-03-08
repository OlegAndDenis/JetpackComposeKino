package com.example.kino.db

import com.example.kino.db.model.CheckColumnDB
import com.example.kino.network.model.GenresApi
import io.reactivex.Single

interface DatabaseRepository {

    fun isNotEmptyGenresAll() : Single<CheckColumnDB>

    fun insertGenres(listGenres : List<GenresApi>, type : String)
}