package com.example.kino.screen.common.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.GenresViewHolderLayoutBinding
import com.example.kino.db.model.Genres

class GenresViewHolder private constructor(
    private val binding: GenresViewHolderLayoutBinding,
    private val onClick: (Int) -> Unit,
) :
    BindHolder<Genres>(binding) {

    constructor(parent: ViewGroup, onClick: (Int) -> Unit) : this(
        GenresViewHolderLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick)

    override fun bind(item: Genres, position: Int) {
        binding.titleGenres.text = item.name
    }


    override fun onClick(v: View?) {
        onClick(adapterPosition)
    }
}