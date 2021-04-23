package com.example.kino.screen.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.R
import com.example.kino.TitleInRecycler
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.TitleViewHolderBinding
import com.example.kino.network.model.common.NetworkItem

class TitleSearchViewHolder(itemView: View) : BindHolder<NetworkItem>(itemView) {

    private var binding: TitleViewHolderBinding = TitleViewHolderBinding.bind(itemView)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.title_view_holder, parent, false))


    override fun bind(item: NetworkItem, position: Int) {
        item as TitleInRecycler
        binding.title.text = "${item.title} найдено ${item.count}"
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }
}