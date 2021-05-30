package com.example.kino

import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Flow<T>.launchView(viewLifecycleOwner: LifecycleOwner) =
    viewLifecycleOwner.lifecycleScope.launch {
        this@launchView.collectLatest {}
    }

fun <T> Flow<T>.launchThis(lifecycleScope: LifecycleCoroutineScope) =
    lifecycleScope.launch {
        this@launchThis.collectLatest {}
    }