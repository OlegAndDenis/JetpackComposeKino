package com.example.kino

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.SearchResultBinding
import com.example.kino.network.model.common.NetworkItem

class SearchViewHolder  private constructor(itemView: View) : BindHolder<NetworkItem>(itemView) {

    private var mBinding: SearchResultBinding = SearchResultBinding.bind(itemView)

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.search_result, parent, false))

    override fun onClick(v: View?) {
    }

    override fun bind(item: NetworkItem, position: Int) {
    }
}