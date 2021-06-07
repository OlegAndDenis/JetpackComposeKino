package com.example.kino.screen.movie

import android.view.View

enum class MovieUiState(val gag: Int, val recyclerview: Int) {
    ConnectionAvailable(View.GONE, View.VISIBLE),
    ConnectionLost(View.VISIBLE, View.GONE)
}