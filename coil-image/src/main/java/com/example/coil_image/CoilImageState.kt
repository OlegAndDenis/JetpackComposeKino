package com.example.coil_image

import android.graphics.drawable.Drawable
import androidx.compose.ui.graphics.ImageBitmap

sealed class CoilImageState {

    object Init : CoilImageState()
    data class Loading(val progress: Float) : CoilImageState()
    data class Success(val imageBitmap: ImageBitmap?) : CoilImageState()
    data class Failure(val errorDrawable: Drawable?) : CoilImageState()
}

fun ImageLoadState.toCoilImageState(): CoilImageState {
    return when (this) {
        is ImageLoadState.Init -> CoilImageState.Init
        is ImageLoadState.Loading -> CoilImageState.Loading(progress)
        is ImageLoadState.Success -> CoilImageState.Success(imageBitmap)
        is ImageLoadState.Failure -> CoilImageState.Failure(data as? Drawable)
    }
}