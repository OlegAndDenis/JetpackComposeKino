package com.example.kino.db

import com.example.kino.db.model.CheckColumnDB
import com.example.kino.db.model.Genres
import com.example.kino.network.model.GenresApi
import io.reactivex.Single

class DatabaseRepositoryImpl(private val dataBase: DataBase) : DatabaseRepository {

    override fun isNotEmptyGenresAll(): Single<CheckColumnDB> {
        return dataBase.movieDao().isNotEmptyGenresAll()
    }

    override fun insertGenres(listGenres: List<GenresApi>, type: String) {
        val listGenresDb = mutableListOf<Genres>()
        listGenres.forEach { list ->
            list.let {
                listGenresDb.add(Genres(idGenres = it.id, name = it.name, type = type))
            }
        }
        dataBase.movieDao().insertAll(listGenresDb)
    }
}