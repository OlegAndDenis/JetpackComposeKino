package com.example.kino.screen.common.itemdecrator

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.core.view.marginStart
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.R

class IndicatorDecoration() : RecyclerView.ItemDecoration() {

    private val colorActive = R.color.design_default_color_primary
    private val colorInactive = R.color.black

    private val DP = Resources.getSystem().displayMetrics.density


    private val indicatorHeight = (DP * 16).toInt()

    private val indicatorStrokeWidth = 5F

    private val indicatorItemLength = DP * 16

    private val indicatorItemPadding = DP * 4

    private val interpolator: AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()

    private val paint = Paint()
    private val activePaint = Paint()

    init {
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = indicatorStrokeWidth
        paint.style = Paint.Style.STROKE
        paint.isAntiAlias = true

        activePaint.strokeCap = Paint.Cap.ROUND
        activePaint.strokeWidth = indicatorStrokeWidth
        activePaint.style = Paint.Style.FILL
        activePaint.isAntiAlias = true
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val adapter = parent.adapter
        if (adapter != null) {
            val itemCount = if (adapter.itemCount == 6) {
                adapter.itemCount - 1
            } else {
                adapter.itemCount
            }

            val totalLength = indicatorItemLength * itemCount
            val paddingBetweenItems = (itemCount - 1).coerceAtLeast(0) * indicatorItemPadding
            val indicatorTotalWidth = totalLength + paddingBetweenItems - 50F
            val indicatorStartX = (parent.width - indicatorTotalWidth) / 2F

            val indicatorPosY = parent.height - indicatorHeight / 2f

            val layoutManager = parent.layoutManager as LinearLayoutManager
            val activePosition = layoutManager.findFirstVisibleItemPosition()
            if (activePosition == RecyclerView.NO_POSITION) {
                return
            }
            if (activePosition == itemCount) {
                return
            }

            val activeChild = layoutManager.findViewByPosition(activePosition)
            val left = activeChild!!.left - activeChild.marginStart
            val width = activeChild.width

            val progress: Float = interpolator.getInterpolation(left * -1 / width.toFloat())
            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float, itemCount: Int,
    ) {
        drawInactive(c, indicatorStartX, indicatorPosY, highlightPosition, itemCount, progress)

        activePaint.color = colorActive
        val itemWidth = indicatorItemLength + indicatorItemPadding
        if (progress == 0f) {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition

            c.drawCircle(highlightStart, indicatorPosY,
                10F, activePaint)
        } else {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            val partialLength = indicatorItemLength * progress

            c.drawCircle(highlightStart + partialLength, indicatorPosY,
                7F, activePaint)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun drawInactive(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, itemCount: Int, progress: Float,
    ) {
        paint.color = colorInactive

        val itemWidth2 = indicatorItemLength + indicatorItemPadding
        var start = indicatorStartX
        for (i in 0 until itemCount) {
            start += if (i == highlightPosition && progress == 0F) {
                c.drawCircle(start, indicatorPosY, 10F, paint)
                itemWidth2
            } else {
                c.drawCircle(start, indicatorPosY, 3F, paint)
                itemWidth2
            }
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = indicatorHeight
    }
}