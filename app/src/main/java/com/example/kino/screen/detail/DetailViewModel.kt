package com.example.kino.screen.detail

import androidx.lifecycle.ViewModel
import com.example.kino.db.DatabaseRepository
import com.example.kino.network.NetworkRepository

class DetailViewModel(
    private val mNetworkRepository: NetworkRepository,
    private val mDatabaseRepository: DatabaseRepository,
): ViewModel() {

    fun requestId(id: String) {

    }
}