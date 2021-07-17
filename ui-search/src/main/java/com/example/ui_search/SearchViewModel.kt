package com.example.ui_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.base.network.model.ConnectionType
import com.example.themdb_api.common.MovieResult
import com.example.themdb_api.common.PersonResult
import com.example.themdb_api.common.SerialResult
import com.example.themdb_api.themdbrepository.ThemdbRepository
import com.example.ui_search.data.SearchUi
import com.example.ui_search.data.SmallData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    network: StateFlow<ConnectionType>,
    private val themdbRepository: ThemdbRepository
) : ViewModel() {

    private val _result: MutableSharedFlow<List<SearchUi>> = MutableSharedFlow()
    val result: SharedFlow<List<SearchUi>> = _result.asSharedFlow()

    fun search(query: String) {
        viewModelScope.launch {
            val uiResult = mutableListOf<SearchUi>()
            val searchResult = themdbRepository.getSearchingResult(query)
            val smallDataPerson = mutableListOf<SmallData>()
            val smallDataMovie = mutableListOf<SmallData>()
            val smallDataSerial = mutableListOf<SmallData>()
            searchResult.result.forEach {
                when (it) {
                    is PersonResult -> {
                        smallDataPerson.add(SmallData(it.id.toString(), it.name, it.profilePath))
                    }
                    is MovieResult -> {
                        smallDataMovie.add(SmallData(it.id.toString(), it.title, it.posterPath))
                    }
                    is SerialResult -> {
                        smallDataSerial.add(SmallData(it.id.toString(), it.name, it.posterPath))
                    }
                }
            }
            if (smallDataMovie.isNotEmpty())
                uiResult.add(SearchUi("movie", query, smallDataMovie))
            if (smallDataSerial.isNotEmpty())
                uiResult.add(SearchUi("tv", query, smallDataSerial))
            if (smallDataPerson.isNotEmpty())
                uiResult.add(SearchUi("person", query, smallDataPerson))
            _result.emit(uiResult)
        }
    }
}