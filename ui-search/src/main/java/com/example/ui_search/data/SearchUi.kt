package com.example.ui_search.data

data class SearchUi(
    val type: String,
    val queue: String,
    val content: List<SmallData> = emptyList()
)
