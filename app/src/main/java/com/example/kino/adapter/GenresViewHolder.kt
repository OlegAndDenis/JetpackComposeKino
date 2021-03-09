package com.example.kino.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.GenresLayoutBinding
import com.example.kino.db.model.Genres

class GenresViewHolder private constructor(itemView: View): BindHolder<Genres>(itemView) {

    private var mBinding: GenresLayoutBinding = GenresLayoutBinding.bind(itemView)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.genres_layout, parent, false))

    override fun bind(item: Genres, position: Int) {
        mBinding.titleGenres.text = item.name
    }
}