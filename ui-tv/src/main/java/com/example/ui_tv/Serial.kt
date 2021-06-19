package com.example.ui_tv

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.ui_common_compose.extensions.rememberFlowWithLifecycle

@Composable
fun Serial() {
    val viewModule: SerialViewModule = hiltViewModel()
    viewModule.loadGenres()
    val state = rememberFlowWithLifecycle(flow = viewModule.serials)
        .collectAsState(initial = SerialState.Loading).value
    when(state) {
        is SerialState.Loading -> { }
        is SerialState.Result -> {

        }
        else -> {

        }
    }
}
