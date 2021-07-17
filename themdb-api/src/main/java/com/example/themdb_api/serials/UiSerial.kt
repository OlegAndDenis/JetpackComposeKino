package com.example.themdb_api.serials

import com.example.themdb_api.common.SerialResult

data class UiSerial(
    val name: String = "",
    val genreId: Int = -99,
    val serials: List<SerialResult> = emptyList()
)
