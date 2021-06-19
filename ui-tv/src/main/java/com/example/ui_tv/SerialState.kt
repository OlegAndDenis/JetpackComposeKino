package com.example.ui_tv

import com.example.themdb_api.serials.UiSerial

sealed class SerialState {

    object Loading : SerialState()

    data class Result(
        val uiMovies: List<UiSerial> = emptyList(),
        val top: UiSerial = UiSerial()
    ) : SerialState()

    object ConnectionLost : SerialState()
    object ConnectionAvailable : SerialState()
}