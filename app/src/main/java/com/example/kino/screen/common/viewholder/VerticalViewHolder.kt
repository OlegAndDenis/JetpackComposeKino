package com.example.kino.screen.common.viewholder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.VerticalViewHolderBinding
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.screen.common.model.GenresList
import com.example.kino.screen.common.viewholder.HorizontalViewHolder
import com.example.kino.screen.movie.viewholder.MovieViewHolder

class VerticalViewHolder private constructor(
    private val binding: VerticalViewHolderBinding,
    private val onClickMovie: (String) -> Unit,
    private val onClickCategory: (GenresList) -> Unit,
) : BindHolder<GenresList>(binding) {

    constructor(
        parent: ViewGroup,
        onClick: (String) -> Unit,
        onClickCategory: (GenresList) -> Unit,
    ) : this(
        VerticalViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        onClick,
        onClickCategory)

    private val pagerSnap: PagerSnapHelper = PagerSnapHelper()

    override fun bind(item: GenresList, position: Int) {
        binding.title.text = item.name
        binding.horizontal.setHasFixedSize(true)
        binding.title.setOnClickListener {
            onClickCategory(item)
        }

        val adapter = CommonAdapter(object : HolderCreator<MovieResult> {
            override fun create(parent: ViewGroup, viewType: Int): BindHolder<MovieResult> {
                return when (item.type) {
                    -1 -> MovieViewHolder(parent,
                        onClickMovie)
                    else -> HorizontalViewHolder(parent, onClickMovie)
                }
            }
        })

        adapter.setTList(item.listMovie)

        binding.horizontal.adapter = adapter
        if (item.type == -1) {
            pagerSnap.attachToRecyclerView(binding.horizontal)
            binding.line.visibility = View.GONE
        } else {
            pagerSnap.attachToRecyclerView(null)
            binding.line.visibility = View.VISIBLE
        }
    }

    fun isRecycled() {
        binding.horizontal.adapter = null
    }

    override fun onClick(v: View?) {
    }
}