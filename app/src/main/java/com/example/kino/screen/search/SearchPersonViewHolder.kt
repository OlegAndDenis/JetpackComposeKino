package com.example.kino.screen.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.DataDiffUtils
import com.example.kino.R
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.SearchPersonBinding
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.person.PersonResult

class SearchPersonViewHolder private constructor(itemView: View) : BindHolder<NetworkItem>(itemView) {

    private var mBinding: SearchPersonBinding = SearchPersonBinding.bind(itemView)
    private val searchAdapter = SearchAdapter()

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.search_person, parent, false))

    override fun bind(item: NetworkItem, position: Int) {
        item as PersonResult
        mBinding.title.text = "Актер"
        mBinding.name.text = item.name
        searchAdapter.setType("person")
        mBinding.otherMovie.apply {
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false).also { this.layoutManager = it }
            adapter = searchAdapter
        }
        if (item.knownFor.isEmpty()) {
            mBinding.otherMovie.visibility = View.GONE
        } else {
            mBinding.otherMovie.visibility = View.VISIBLE
        }
        diffUtils(searchAdapter.getTList(), item.knownFor)
    }

    private fun diffUtils(oldItems: List<NetworkItem>, nextItems: List<NetworkItem>) {
        val tDiffUtil: DataDiffUtils<NetworkItem> = DataDiffUtils(oldItems, nextItems)
        val result = DiffUtil.calculateDiff(tDiffUtil)
        searchAdapter.setTList(nextItems)
        result.dispatchUpdatesTo(searchAdapter)
    }

    override fun onClick(v: View?) {
    }

}