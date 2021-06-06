package com.example.kino.adapter

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.screen.common.viewholder.VerticalViewHolder
import com.example.kino.adapter.holder.BindHolder

open class CommonAdapter<T>(private val holderCreator: HolderCreator<T>) :
    RecyclerView.Adapter<BindHolder<T>>() {

    private var mTList: MutableList<T> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindHolder<T> {
        return holderCreator.create(parent, viewType)
    }

    override fun onBindViewHolder(@NonNull holder: BindHolder<T>, position: Int) {
        val item = mTList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return mTList.size
    }

    fun initTList(tList: List<T>) {
        mTList = tList.toMutableList()
        notifyDataSetChanged()
    }

    fun setTList(tList: List<T>) {
        mTList.addAll(tList)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 5) {
            1
        } else {
            0
        }
    }

    override fun onViewRecycled(holder: BindHolder<T>) {
        super.onViewRecycled(holder)
        if (holder is VerticalViewHolder) {
            holder.isRecycled()
        }
    }

    interface HolderCreator<T> {
        fun create(parent: ViewGroup, viewType: Int): BindHolder<T>
    }
}