package com.example.kino.listener

import androidx.recyclerview.widget.RecyclerView

class OnScrollListener(private val verticalScrollListener: OnVerticalScrollListener) :
    RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (!recyclerView.canScrollVertically(-1)) {
            verticalScrollListener.onScrolledToTop()
        } else if (!recyclerView.canScrollVertically(1)) {
            verticalScrollListener.onScrolledToBottom()
        } else if (dy < 0) {
            verticalScrollListener.onScrolledUp()
        } else if (dy > 0) {
            verticalScrollListener.onScrolledDown()
        }
    }
}