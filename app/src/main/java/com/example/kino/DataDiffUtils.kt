package com.example.kino

import androidx.recyclerview.widget.DiffUtil

class DataDiffUtils<T>(private val oldData: List<T?>, private val newData: List<T?>) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldData.size
    }

    override fun getNewListSize(): Int {
        return newData.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData.size != newData.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldData[oldItemPosition] == newData[newItemPosition]
                && oldData[oldItemPosition].hashCode() == newData[newItemPosition].hashCode()
    }
}