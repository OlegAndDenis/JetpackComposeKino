package com.example.kino.screen.allmovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.GlideManage
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.AllViewHolderLayoutBinding
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieResult

class AllViewHolder private constructor(itemView: View, private val onClick: (Int) -> Unit) :
    BindHolder<NetworkItem>(itemView) {

    private val binding: AllViewHolderLayoutBinding = AllViewHolderLayoutBinding.bind(itemView)

    constructor(parent: ViewGroup, onClick: (Int) -> Unit) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.all_view_holder_layout, parent, false), onClick)

    override fun bind(item: NetworkItem, position: Int) {
        when(item) {
            is MovieResult -> GlideManage.with(itemView).loadImage(item.posterPath, binding.image)
        }
    }

    override fun onClick(v: View?) {
        onClick
    }
}