package com.example.coil_image

import androidx.compose.ui.graphics.ImageBitmap

sealed class ImageLoadState {

    object Init : ImageLoadState()
    data class Loading(val progress: Float) : ImageLoadState()
    data class Success(val imageBitmap: ImageBitmap?) : ImageLoadState()
    data class Failure(val data: Any?) : ImageLoadState()
}