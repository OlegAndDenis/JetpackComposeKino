package com.example.ui_common_compose.animation

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import kotlin.math.abs

class FlingBehavior(
    private val flingDecay: DecayAnimationSpec<Float>
): FlingBehavior {
    override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        return if (abs(initialVelocity) > 1f) {
            var velocityLeft = initialVelocity
            var lastValue = 0f
            AnimationState(
                initialValue = 0f,
                initialVelocity = initialVelocity,
            ).animateDecay(flingDecay, sequentialAnimation = true) {
                val delta = (value - lastValue) / 1.6F
                val consumed = scrollBy(delta)
                lastValue = value
                velocityLeft = this.velocity
                if (abs(delta - consumed) > 0.3f) this.cancelAnimation()
            }
            velocityLeft
        } else {
            initialVelocity
        }
    }
}

@Composable
fun <T> rememberSplineDecay(): DecayAnimationSpec<T> {

    val density = LocalDensity.current
    return remember(density.density) {
        FloatExponentialDecaySpec(0.5F, 5F).generateDecayAnimationSpec()
    }
}