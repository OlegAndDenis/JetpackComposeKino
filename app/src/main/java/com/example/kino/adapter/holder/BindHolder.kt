package com.example.kino.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BindHolder<T>(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    init {
        itemView.setOnClickListener { onClick(it) }
    }

    abstract fun bind(item: T, position: Int)

    abstract fun onClick(v: View?)
}