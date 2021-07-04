package com.example.ui_common_compose.animation

import android.annotation.SuppressLint
import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp

@SuppressLint("ComposableModifierFactory")
@Composable
fun Modifier.scaleAnimation(
    state: LazyListState,
    @FloatRange(from = 0.0, to = 1.0) animationSize: Float,
    @FloatRange(from = 0.0, to = 2.0) animationSpeed: Float = 1.5F,
): Modifier {

    val offset =
        state.firstVisibleItemScrollOffset * (animationSize / 1000)

    val transaction = updateTransition(targetState = offset, label = "scaleAnimation")

    val scale by transaction.animateFloat(
        transitionSpec = { spring(dampingRatio = offset.coerceIn(0F, 0.5F)) },
        label = ""
    ) {
        1f - it.coerceIn(0f, 1f) / animationSpeed
    }

    return graphicsLayer {
        lerp(
            start = 0.6F,
            stop = 1F,
            fraction = scale
        ).also {
            scaleX = it
            scaleY = it
        }

        alpha = lerp(
            start = 0.2f,
            stop = 1f,
            fraction = 1f - (offset * 2).coerceIn(0f, 1f) * animationSpeed
        )
    }
}