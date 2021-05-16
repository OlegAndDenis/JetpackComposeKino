package com.example.kino.screen.allmovie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.GlideManage
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.AllViewHolderLayoutBinding
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieResult

class AllViewHolder private constructor(
    private val binding: AllViewHolderLayoutBinding,
    private val onClick: (Long) -> Unit
) : BindHolder<NetworkItem>(binding) {

    private var id: Long = 0

    constructor(parent: ViewGroup, onClick: (Long) -> Unit) : this(
        AllViewHolderLayoutBinding.inflate(LayoutInflater.from(parent.context),
        parent, false), onClick)

    override fun bind(item: NetworkItem, position: Int) {
        when(item) {
            is MovieResult -> {
                id = item.id
                GlideManage.with(itemView).loadImage(item.posterPath, binding.image)
            }
        }
    }

    override fun onClick(v: View?) {
        onClick(id)
    }
}