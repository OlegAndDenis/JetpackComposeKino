package com.example.kino.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView


abstract class BindHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {

    abstract fun bind(item: T, position: Int)
}