package com.example.coil_image

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.LifecycleOwner
import coil.ImageLoader
import coil.request.CachePolicy
import coil.request.Disposable
import coil.request.ImageRequest
import coil.size.Precision
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine

/**
 * This method load image and for loading used CircularProgressIndicator.
 * For failure used default Text("image request failed")
 *
 * Set the data to load.
 * The default supported data types are: (@link ImageRequest#data)
 */
@Composable
fun CoilImageWithCircularProgress(
    data: Any,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    indicatorColor: Color = Color.Red,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
) {
    CoilBuilder(
        data = data,
        modifier = modifier,
        contentScale = contentScale,
        loading = {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally),
                    color = indicatorColor,
                    strokeWidth = strokeWidth
                )
            }
        }
    )
}

/**
 * Builder for load image.
 * This method collects all data for loading.
 * Return loading state.
 *
 * If not callback (success, Failure), used default value.
 */
@Composable
private fun CoilBuilder(
    data: Any,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    context: Context = LocalContext.current,
    alignment: Alignment = Alignment.Center,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    contentScale: ContentScale = ContentScale.Crop,
    success: @Composable ((imageState: CoilImageState.Success) -> Unit)? = null,
    loading: @Composable ((ImageLoader: CoilImageState.Loading) -> Unit)? = null,
    failure: @Composable ((imageState: CoilImageState.Failure) -> Unit)? = null,
) {
    CoilLoadImage(
        request = ImageRequest.Builder(context)
            .data(data)
            .lifecycle(lifecycleOwner)
            .diskCachePolicy(CachePolicy.ENABLED)
            .premultipliedAlpha(true)
            .precision(Precision.AUTOMATIC)
            .build(),
        modifier = modifier.fillMaxWidth()
    ) { imageState ->
        when (val coilImageState = imageState.toCoilImageState()) {
            is CoilImageState.Init -> Unit
            is CoilImageState.Loading -> loading?.invoke(coilImageState)
            is CoilImageState.Failure -> {
                if (failure != null) failure.invoke(coilImageState)
                else Text(text = "image request failed")
            }
            is CoilImageState.Success -> {
                if (success != null) {
                    success.invoke(coilImageState)
                } else {
                    coilImageState.imageBitmap?.let {
                        Image(
                            bitmap = it,
                            contentDescription = "",
                            modifier = modifier,
                            alignment = alignment,
                            contentScale = contentScale,
                            alpha = alpha,
                            colorFilter = colorFilter,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
private fun CoilLoadImage(
    request: ImageRequest,
    modifier: Modifier = Modifier,
    content: @Composable (imageState: ImageLoadState) -> Unit
) {
    val context = LocalContext.current
    val imageLoadStateFlow =
        remember { MutableStateFlow<ImageLoadState>(ImageLoadState.Loading(0f)) }
    val disposable = remember { mutableStateOf<Disposable?>(null) }

    CustomLoad(
        imageRequest = request,
        executeImageRequest = {
            suspendCancellableCoroutine { dispose ->
                disposable.value = ImageLoader.invoke(context).enqueue(
                    request.newBuilder(context).target(
                        onSuccess = {
                            imageLoadStateFlow.value =
                                ImageLoadState.Success(it.toBitmap().asImageBitmap())
                        },
                        onError = {
                            imageLoadStateFlow.value =
                                ImageLoadState.Failure(it?.toBitmap()?.asImageBitmap())
                        }
                    ).build()
                )
                dispose.resume(imageLoadStateFlow) {
                    disposable.value?.dispose()
                }
            }
        },
        modifier = modifier,
        content = content
    )
}
