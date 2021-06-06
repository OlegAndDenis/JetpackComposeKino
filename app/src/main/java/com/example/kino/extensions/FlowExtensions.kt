package com.example.kino.extensions

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber

fun <T> Flow<T>.launchViewWhenStarted(viewLifecycleOwner: LifecycleOwner) =
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        this@launchViewWhenStarted.collectLatest {}
    }

fun <T> Flow<T>.launchThis(lifecycleScope: LifecycleCoroutineScope) =
    lifecycleScope.launch {
        this@launchThis.collectLatest {}
    }

inline fun Fragment.launchViewWhenStartedBlock(
    crossinline block: suspend Wrapper.() -> Unit
) = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
    Wrapper(this).block()
}

class Wrapper(val scope: CoroutineScope) {
    fun <T> Flow<T>.collect(action: suspend (T) -> Unit) =
        this
            .onEach(action)
            .catch { Timber.e(it) }
            .launchIn(scope)
}