package com.example.kino.screen.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.OtherMoviesBinding
import com.example.kino.databinding.PopularityViewBinding
import com.example.kino.databinding.SearchMovieBinding
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.network.model.serial.SerialsResult

class OtherMovieAndSerialImageViewHolder private constructor(itemView: View) : BindHolder<NetworkItem>(itemView) {

    private var mBinding: OtherMoviesBinding = OtherMoviesBinding.bind(itemView)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
        .inflate(R.layout.other_movies, parent, false))

    override fun bind(item: NetworkItem, position: Int) {
        if (item is MovieResult) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500/${item.posterPath}")
                .thumbnail(0.5f)
                .centerCrop()
                .error(Glide
                    .with(itemView)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500/${item.posterPath}"))
                .into(mBinding.image)
        } else if (item is SerialsResult) {
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w500/${item.posterPath}")
                .thumbnail(0.5f)
                .centerCrop()
                .error(Glide
                    .with(itemView)
                    .asBitmap()
                    .load("https://image.tmdb.org/t/p/w500/${item.posterPath}"))
                .into(mBinding.image)
        }

    }

    override fun onClick(v: View?) {
    }
}