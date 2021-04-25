package com.example.kino.screen

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.R


class IndicatorDecoration() : RecyclerView.ItemDecoration() {

    private val colorActive = R.color.design_default_color_primary
    private val colorInactive = R.color.black

    private val DP = Resources.getSystem().displayMetrics.density


    private val mIndicatorHeight = (DP * 16).toInt()

    private val mIndicatorStrokeWidth = DP * 2

    private val mIndicatorItemLength = DP * 16

    private val mIndicatorItemPadding = DP * 6

    private val mInterpolator: AccelerateDecelerateInterpolator = AccelerateDecelerateInterpolator()

    private val mPaint = Paint()
    private val activePaint = Paint()

    init {
        mPaint.strokeCap = Paint.Cap.ROUND
        mPaint.strokeWidth = mIndicatorStrokeWidth
        mPaint.style = Paint.Style.STROKE
        mPaint.isAntiAlias = true

        activePaint.strokeCap = Paint.Cap.ROUND
        activePaint.strokeWidth = mIndicatorStrokeWidth
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

            val totalLength = mIndicatorItemLength * itemCount
            val paddingBetweenItems = (itemCount - 1).coerceAtLeast(0) * mIndicatorItemPadding
            val indicatorTotalWidth = totalLength + paddingBetweenItems
            val indicatorStartX = (parent.width - indicatorTotalWidth) / 2f

            val indicatorPosY = parent.height - mIndicatorHeight / 2f

            val layoutManager = parent.layoutManager as LinearLayoutManager
            val activePosition = layoutManager.findFirstVisibleItemPosition()
            if (activePosition == RecyclerView.NO_POSITION) {
                return
            }
            if (activePosition == itemCount) {
                return
            }
            val activeChild = layoutManager.findViewByPosition(activePosition)
            val left = activeChild!!.left
            val width = activeChild.width

            val progress: Float = mInterpolator.getInterpolation(left * -1 / width.toFloat())
            drawHighlights(c, indicatorStartX, indicatorPosY, activePosition, progress, itemCount)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun drawHighlights(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, progress: Float, itemCount: Int,
    ) {
        drawInactive(c, indicatorStartX, indicatorPosY, highlightPosition, itemCount)

        activePaint.color = colorActive
        val itemWidth = mIndicatorItemLength + mIndicatorItemPadding
        if (progress == 0f) {
            val highlightStart = indicatorStartX + itemWidth * highlightPosition
            c.drawCircle(highlightStart, indicatorPosY,
                10F, activePaint)
        } else {
            var highlightStart = indicatorStartX + itemWidth * highlightPosition
            val partialLength = mIndicatorItemLength * progress

            c.drawCircle(highlightStart + partialLength, indicatorPosY,
                10F, activePaint)

            if (highlightPosition < itemCount - 1) {
                highlightStart += itemWidth
                c.drawCircle(highlightStart, indicatorPosY,
                    10F, activePaint)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun drawInactive(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int, itemCount: Int,
    ) {
        mPaint.color = colorInactive

        val itemWidth2 = mIndicatorItemLength + mIndicatorItemPadding
        var start = indicatorStartX
        for (i in 0 until itemCount) {
            start += if (i != highlightPosition) {
                c.drawCircle(start, indicatorPosY, 3F, mPaint)
                itemWidth2
            } else {
                c.drawCircle(start, indicatorPosY, 10F, mPaint)
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
        outRect.bottom = mIndicatorHeight
    }
}