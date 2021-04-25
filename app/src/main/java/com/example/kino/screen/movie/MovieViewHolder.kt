package com.example.kino.screen.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.MovieViewHolderLayoutBinding
import com.example.kino.network.model.movie.MovieResult

class MovieViewHolder private constructor(itemView: View) : BindHolder<MovieResult>(itemView) {

    private var mBinding: MovieViewHolderLayoutBinding = MovieViewHolderLayoutBinding.bind(itemView)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_view_holder_layout, parent, false))

    override fun bind(item: MovieResult, position: Int) {
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w500/${item.backdropPath}")
            .into(mBinding.moviePopularity)
        mBinding.movieTitle.text = item.title
    }

    override fun onClick(v: View?) {
    }
}