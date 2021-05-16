package com.example.kino.screen.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.GlideManage
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.MovieViewHolderLayoutBinding
import com.example.kino.network.model.movie.MovieResult

class MovieViewHolder private constructor(
    private val binding: MovieViewHolderLayoutBinding,
    private val onClick: (String) -> Unit,
) : BindHolder<MovieResult>(binding) {

    constructor(parent: ViewGroup, onClick: (String) -> Unit) : this(
        MovieViewHolderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick)

    private var id: String = ""

    override fun bind(item: MovieResult, position: Int) {
        GlideManage.with(itemView)
            .loadImage(item.backdropPath, binding.moviePopularity)
        binding.movieTitle.text = item.title
        id = item.id.toString()
    }

    override fun onClick(v: View?) {
        onClick(id)
    }
}