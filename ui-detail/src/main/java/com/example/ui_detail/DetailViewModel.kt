package com.example.ui_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.themdb_api.themdbrepository.ThemdbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: ThemdbRepository
) : ViewModel() {

    init {
        val id = savedStateHandle.get<String>("id") ?: ""
        viewModelScope.launch {
            val movie = repository.loadDetail(id)
        }
    }
}