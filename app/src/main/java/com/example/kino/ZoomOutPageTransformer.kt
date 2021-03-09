package com.example.kino

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class ZoomOutPageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width
        val pageHeight = view.height
        view.scrollBarFadeDuration = 1000
        if (position < -1) {
            view.alpha = 0f
        } else if (position <= 1) {
            val scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position))
            val vertMargin = pageHeight * (1 - scaleFactor) / 2
            val hornMargin = pageWidth * (1 - scaleFactor) / 2
            if (position < 0) {
                view.translationX = hornMargin - vertMargin / 4
            } else {
                view.translationX = -hornMargin + vertMargin / 4
            }
            view.scaleX = scaleFactor
            view.scaleY = scaleFactor
            view.alpha = MIN_ALPHA +
                    (scaleFactor - MIN_SCALE) /
                    (1 - MIN_SCALE) * (1 - MIN_ALPHA)
        } else {
            view.alpha = 0f
        }
    }

    companion object {
        private const val MIN_SCALE = 0.7f
        private const val MIN_ALPHA = 0.4f
    }
}