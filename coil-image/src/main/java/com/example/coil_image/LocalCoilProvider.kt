package com.example.coil_image

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.imageLoader

val LocalCoilImageLoader = staticCompositionLocalOf<ImageLoader?> { null }

internal object LocalCoilProvider {

    @Composable
    fun getCoilImageLoader(): ImageLoader =
        LocalCoilImageLoader.current ?: LocalContext.current.imageLoader
}