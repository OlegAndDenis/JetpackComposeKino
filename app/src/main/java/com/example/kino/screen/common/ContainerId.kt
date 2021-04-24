package com.example.kino.screen.common

import androidx.annotation.IdRes
import com.example.kino.R

enum class ContainerId(@IdRes val id: Int) {
    GLOBAL_FRAME(R.id.common_frame),
    BOTTOM_NAVIGATION_FRAME(R.id.container_frame)
}