package com.example.kino.screen.common

import androidx.annotation.IdRes
import com.example.kino.R

enum class ScreenEnum(val transactionTag: String) {
    SEARCH("search"),
    MOVIE("movie"),
    SERIAL("serial"),
    FAVORITE("movie"),
    SPLASH("splash"),
    COMMONVIEW("common"),
    DETAIL("detail"),
    NONE("none");

    companion object {
        fun findById(@IdRes id: Int): ScreenEnum =
            when (id) {
                R.id.butt_search -> SEARCH
                R.id.butt_film -> MOVIE
                R.id.butt_serial -> SERIAL
                R.id.butt_favorite -> FAVORITE
                else -> NONE
            }
    }
}