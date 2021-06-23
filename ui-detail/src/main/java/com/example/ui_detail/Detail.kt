package com.example.ui_detail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun Detail() {
    val viewModel: DetailViewModel = hiltViewModel()
}