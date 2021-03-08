package com.example.kino.adapter

import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.adapter.holder.BindHolder


class CommonAdapter<T>(holderCreator: HolderCreator<T>): RecyclerView.Adapter<BindHolder<T>>() {

    private var mTList: MutableList<T> = mutableListOf()
    private val holderCreat: HolderCreator<T> = holderCreator

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindHolder<T> {
        return holderCreat.create(parent, viewType)
    }

    override fun onBindViewHolder(@NonNull holder: BindHolder<T>, position: Int) {
        val item = mTList[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int {
        return mTList.size
    }

    fun getTList(): List<T> {
        return mTList
    }

    fun setTList(tList: List<T>) {
        mTList.addAll(tList)
        notifyDataSetChanged()
    }

    interface HolderCreator<T> {
        @NonNull
        fun create(parent: ViewGroup, viewType: Int): BindHolder<T>
    }
}