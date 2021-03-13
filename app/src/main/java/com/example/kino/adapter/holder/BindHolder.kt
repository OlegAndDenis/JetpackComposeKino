package com.example.kino.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class BindHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    init {
        itemView.setOnClickListener(this@BindHolder)
    }

    abstract fun bind(item: T, position: Int)

    abstract override fun onClick(v: View?)
}