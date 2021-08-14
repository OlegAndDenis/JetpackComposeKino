package com.example.ui_search

import com.example.base.network.base.ViewState
import com.example.ui_search.data.SearchUi

sealed class SearchState: ViewState {
    object Init: SearchState()
    object Loading : SearchState()
    data class Result(val result: List<SearchUi> = emptyList()) : SearchState()
    data class NoResult(val result: String): SearchState()
    object ConnectionLost : SearchState()
    object ConnectionAvailable : SearchState()
}