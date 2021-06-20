package com.example.themdb_api.serials

data class UiSerial(
    val name: String = "",
    val genreId: Int = -99,
    val serials: List<SerialResult> = emptyList()
)
