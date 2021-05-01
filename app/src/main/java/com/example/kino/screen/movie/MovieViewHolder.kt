package com.example.kino.screen.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.adapter.holder.ClickItem
import com.example.kino.databinding.MovieViewHolderLayoutBinding
import com.example.kino.loadBackdropPath
import com.example.kino.network.model.movie.MovieResult

class MovieViewHolder private constructor(itemView: View, private val onClick: (Int) -> Unit) :
    BindHolder<MovieResult>(itemView) {

    private var binding: MovieViewHolderLayoutBinding = MovieViewHolderLayoutBinding.bind(itemView)

    constructor(parent: ViewGroup, onClick: (Int) -> Unit) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_view_holder_layout, parent, false), onClick)

    override fun bind(item: MovieResult, position: Int) {
        Glide.with(itemView)
            .loadBackdropPath(item.backdropPath, binding.moviePopularity)
        binding.movieTitle.text = item.title
    }

    override fun onClick(v: View?) {
        onClick(adapterPosition)
    }
}