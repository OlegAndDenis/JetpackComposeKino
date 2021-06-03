package com.example.kino.db

import com.example.kino.screen.common.typeenum.TypeEnum
import com.example.kino.db.model.CheckColumnDB
import com.example.kino.db.model.Genres
import com.example.kino.network.model.common.GenresApi

class DatabaseRepositoryImpl(private val dataBase: DataBase) : DatabaseRepository {

    override suspend fun isNotEmptyGenresAll(): CheckColumnDB {
        return dataBase.movieDao().isNotEmptyGenresAll()
    }

    override fun insertGenres(listGenres: List<GenresApi>, type: TypeEnum) {
        val listGenresDb = mutableListOf<Genres>()
        listGenres.forEach { list ->
            list.let {
                listGenresDb.add(Genres(idGenres = it.id, name = it.name, type = type.type))
            }
        }
        dataBase.movieDao().insertAll(listGenresDb)
    }

    override suspend fun getGenres(type: TypeEnum): List<Genres> {
        return dataBase.movieDao().getAllGenresType("%${type.type}%")
    }
}