package com.example.kino.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BindHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    init {
        itemView.setOnClickListener { onClick(it) }
    }

    abstract fun bind(item: T, position: Int)

    abstract fun onClick(v: View?)
}