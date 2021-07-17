package com.example.ui_movie.movie

import com.example.base.network.base.ViewEvent

sealed class Event : ViewEvent {
    data class CategorySelection(val id: String) : Event()
}