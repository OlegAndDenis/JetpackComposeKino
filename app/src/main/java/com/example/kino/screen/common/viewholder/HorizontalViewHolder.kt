package com.example.kino.screen.common.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.HorizontalViewHolderBinding
import com.example.kino.glide.GlideManage
import com.example.kino.network.model.movie.MovieResult

class HorizontalViewHolder private constructor(
    private val binding: HorizontalViewHolderBinding,
    private val onClick: (String) -> Unit,
) : BindHolder<MovieResult>(binding) {

    constructor(parent: ViewGroup, onClick: (String) -> Unit) : this(
        HorizontalViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick)

    private var id: String = ""

    override fun bind(item: MovieResult, position: Int) {
        if (item.posterPath != "no")
            GlideManage.with(binding.poster).loadRoundPoster(item.posterPath, binding.poster)
        id = item.id.toString()
    }

    override fun onClick(v: View?) {
        onClick(id)
    }
}