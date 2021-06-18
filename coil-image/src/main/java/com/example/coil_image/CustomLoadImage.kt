package com.example.coil_image

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

@Composable
fun <T : Any> CustomLoad(
    imageRequest: T,
    executeImageRequest: suspend () -> StateFlow<ImageLoadState>,
    modifier: Modifier = Modifier,
    content: @Composable (imageState: ImageLoadState) -> Unit
) {
    var state by remember(imageRequest) { mutableStateOf<ImageLoadState>(ImageLoadState.Init) }
    LaunchedEffect(imageRequest) {
        executeImageLoading(
            executeImageRequest
        ).collect {
            state = it
        }
    }
    BoxWithConstraints(modifier) {
        content(state)
    }
}

private suspend fun executeImageLoading(
    executeImageRequest: suspend () -> Flow<ImageLoadState>
) = flow {
    emitAll(executeImageRequest())
}.catch {
    emit(ImageLoadState.Failure(null))
}.distinctUntilChanged()
    .flowOn(Dispatchers.IO)