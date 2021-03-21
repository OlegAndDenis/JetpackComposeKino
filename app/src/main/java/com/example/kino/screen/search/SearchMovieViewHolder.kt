package com.example.kino.screen.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.PopularityViewBinding
import com.example.kino.databinding.SearchMovieBinding
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieResult

class SearchMovieViewHolder private constructor(itemView: View) : BindHolder<NetworkItem>(itemView) {

    private var mBinding: SearchMovieBinding = SearchMovieBinding.bind(itemView)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
        .inflate(R.layout.search_movie, parent, false))

    override fun bind(item: NetworkItem, position: Int) {
        item as MovieResult
        mBinding.title.text = "Фильмы"
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w500/${item.posterPath}")
            .thumbnail(0.5f)
            .centerCrop()
            .error(Glide
                .with(itemView)
                .asBitmap()
                .load("https://image.tmdb.org/t/p/w500/${item.posterPath}"))
            .into(mBinding.searchImage)
        mBinding.searchTitle.text = when {
            item.title != "no" -> {
                item.title
            }
            item.originalTitle != "no" -> {
                item.originalTitle
            }
            else -> {
                ""
            }
        }
    }

    override fun onClick(v: View?) {
        Log.i("OELG", "++++++++");
    }
}