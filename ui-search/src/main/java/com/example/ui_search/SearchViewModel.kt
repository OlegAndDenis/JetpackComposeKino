package com.example.ui_search

import com.example.base.network.base.BaseViewModel
import com.example.base.network.model.ConnectionType
import com.example.themdb_api.common.MovieResult
import com.example.themdb_api.common.PersonResult
import com.example.themdb_api.common.SerialResult
import com.example.themdb_api.themdbrepository.ThemdbRepository
import com.example.ui_search.data.SearchUi
import com.example.ui_search.data.SmallData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    network: StateFlow<ConnectionType>,
    private val themdbRepository: ThemdbRepository
) : BaseViewModel<Event, SearchState, Effect>() {

    override fun setInitialState(): SearchState = SearchState.Init

    override fun handleEvents(event: Event) {
        when (event) {
            is Event.CategorySelection -> {
                setEffect { Effect.Navigation.ToCategoryDetails(event.id) }
            }
        }
    }

    suspend fun search(query: String) {
        setState { SearchState.Loading }
        val searchResult = themdbRepository.getSearchingResult(query)
        if (searchResult.result.isEmpty()) {
            setState { SearchState.NoResult("По данному запросу \"$query\" результат не найден") }
            return
        }
        val uiResult = mutableListOf<SearchUi>()
        val smallDataPerson = mutableListOf<SmallData>()
        val smallDataMovie = mutableListOf<SmallData>()
        val smallDataSerial = mutableListOf<SmallData>()
        searchResult.result.forEach {
            when (it) {
                is PersonResult -> {
                    smallDataPerson.add(SmallData(it.id.toString(), it.name, it.profilePath))
                }
                is MovieResult -> {
                    smallDataMovie.add(
                        SmallData(
                            it.id.toString(),
                            it.title,
                            it.posterPath,
                            it.voteCount
                        )
                    )
                }
                is SerialResult -> {
                    smallDataSerial.add(
                        SmallData(
                            it.id.toString(),
                            it.name,
                            it.posterPath,
                            it.voteCount
                        )
                    )
                }
            }
        }
        if (smallDataMovie.isNotEmpty()) {
            smallDataMovie.sortByDescending { it.voteCount }
            uiResult.add(SearchUi("movie", query, smallDataMovie))
        }
        if (smallDataSerial.isNotEmpty()) {
            smallDataSerial.sortByDescending { it.voteCount }
            uiResult.add(SearchUi("tv", query, smallDataSerial))
        }
        if (smallDataPerson.isNotEmpty())
            uiResult.add(SearchUi("person", query, smallDataPerson))
        setState { SearchState.Result(uiResult) }
    }
}