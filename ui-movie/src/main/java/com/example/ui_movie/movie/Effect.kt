package com.example.ui_movie.movie

import com.example.base.network.base.ViewSideEffect

sealed class Effect: ViewSideEffect {
    object DataWasLoaded : Effect()

    sealed class Navigation : Effect() {
        data class ToCategoryDetails(val id: String) : Navigation()
    }
}