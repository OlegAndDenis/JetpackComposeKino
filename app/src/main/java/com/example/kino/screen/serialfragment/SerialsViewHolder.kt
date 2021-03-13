package com.example.kino.screen.serialfragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.PopularityViewBinding
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.network.model.serial.SerialsResult

class SerialsViewHolder private constructor(itemView: View) : BindHolder<SerialsResult>(itemView) {

    private var mBinding: PopularityViewBinding = PopularityViewBinding.bind(itemView)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
        .inflate(R.layout.popularity_view, parent, false))

    override fun bind(item: SerialsResult, position: Int) {
        Glide.with(itemView)
            .load("https://image.tmdb.org/t/p/w500/${item.backdropPath}")
            .into(mBinding.banerImage)
        mBinding.title.text = item.name
    }
}