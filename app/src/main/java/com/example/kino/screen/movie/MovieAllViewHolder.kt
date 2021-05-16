package com.example.kino.screen.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.GlideManage
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.MovieAllViewHolderLayoutBinding
import com.example.kino.network.model.movie.MovieResult

class MovieAllViewHolder private constructor(
    private val binding: MovieAllViewHolderLayoutBinding,
    private val onClick: (Int) -> Unit,
) : BindHolder<MovieResult>(binding) {

    constructor(parent: ViewGroup, onClick: (Int) -> Unit) : this(
        MovieAllViewHolderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick)

    override fun bind(item: MovieResult, position: Int) {
        GlideManage.with(itemView)
            .loadImage(item.paths,
                binding.one,
                binding.two,
                binding.three,
                binding.four,
                binding.five,
                binding.six,
                binding.seven,
                binding.eight,
                binding.center,
                binding.twelve,
                binding.eleven,
                binding.ten,
                binding.nine)
    }

    override fun onClick(v: View?) {
        onClick(adapterPosition)
    }
}