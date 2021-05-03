package com.example.kino.screen

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.GenresViewHolderLayoutBinding
import com.example.kino.db.model.Genres

class GenresViewHolder private constructor(itemView: View, private val onClick: (Int) -> Unit) :
    BindHolder<Genres>(itemView) {

    private var mBinding: GenresViewHolderLayoutBinding =
        GenresViewHolderLayoutBinding.bind(itemView)

    constructor(parent: ViewGroup, onClick: (Int) -> Unit) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.genres_view_holder_layout, parent, false), onClick)

    override fun bind(item: Genres, position: Int) {
        mBinding.titleGenres.text = item.name
    }


    override fun onClick(v: View?) {
        onClick(adapterPosition)
    }
}