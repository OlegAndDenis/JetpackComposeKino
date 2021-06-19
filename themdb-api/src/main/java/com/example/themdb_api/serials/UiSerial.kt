package com.example.themdb_api.serials

data class UiSerial(
    val name: String = "",
    val movies: List<SerialResult> = emptyList()
)
