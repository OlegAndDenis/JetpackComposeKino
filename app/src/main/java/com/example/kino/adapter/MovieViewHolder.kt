package com.example.kino.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import com.bumptech.glide.Glide
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.PopularityViewBinding
import com.example.kino.network.model.MovieResult

class MovieViewHolder private constructor(itemView: View) : BindHolder<MovieResult>(itemView) {

    private var mBinding: PopularityViewBinding = PopularityViewBinding.bind(itemView)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
        .inflate(R.layout.popularity_view, parent, false))

    override fun bind(item: MovieResult, position: Int) {
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/original/${item.backdropPath}")
            .into(mBinding.banerImage)
        mBinding.title.text = item.title

    }
}