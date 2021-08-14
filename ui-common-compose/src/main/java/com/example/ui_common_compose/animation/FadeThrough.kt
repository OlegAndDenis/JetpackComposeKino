package com.example.ui_common_compose.animation

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.fastForEach

@Composable
fun <T> FadeThrough(
    targetState: T,
    modifier: Modifier = Modifier,
    content: @Composable (T) -> Unit
) {
    val items = remember { mutableStateListOf<FadeThroughAnimationItem<T>>() }
    val transitionState = remember { MutableTransitionState(targetState) }
    val targetChanged = (targetState != transitionState.targetState)
    transitionState.targetState = targetState
    val transition = updateTransition(transitionState, label = "")

    if (targetChanged || items.isEmpty()) {
        val keys = items.map { it.key }.run {
            if (!contains(targetState)) {
                toMutableList().also { it.add(targetState) }
            } else {
                this
            }
        }
        items.clear()
        keys.mapTo(items) { key ->
            FadeThroughAnimationItem(key) {
                val disappearAnimationSpec = tween<Float>(
                    delayMillis = 700,
                    durationMillis = 250
                )
                val appearAnimationSpec = tween<Float>(
                    delayMillis = 700,
                    durationMillis = 250
                )

                val alpha by transition.animateFloat(
                    transitionSpec = { disappearAnimationSpec }, label = ""
                ) { if (it == key) 1f else 0f }

                Box(Modifier.graphicsLayer { this.alpha = alpha }) {
                    content(key)
                }

                val scale by transition.animateFloat(
                    transitionSpec = { appearAnimationSpec }, label = ""
                ) { if (it == key) 1f else 0.7f }

                Box(Modifier.alpha(alpha).scale(scale = scale)) {
                    content(key)
                }
            }
        }
    } else if (transitionState.currentState == transitionState.targetState) {
        items.removeAll { it.key != transitionState.targetState }
    }

    Box(modifier) {
        items.fastForEach {
            key(it.key) {
                it.content()
            }
        }
    }
}

private data class FadeThroughAnimationItem<T>(
    val key: T,
    val content: @Composable () -> Unit
)
