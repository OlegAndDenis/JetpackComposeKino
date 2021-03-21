package com.example.kino

import android.util.Log
import android.view.ViewGroup
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.network.model.person.PersonResult
import com.example.kino.network.model.serial.SerialsResult
import com.example.kino.screen.moviefragment.MovieViewHolder
import com.example.kino.screen.serialfragment.SerialsViewHolder

class SearchAdapter(holderCreator: HolderCreator<NetworkItem>): CommonAdapter<NetworkItem>(holderCreator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> {
        return when (viewType) {
            1 -> PersonViewHolder(parent) as BindHolder<NetworkItem>
            2 -> MovieViewHolder(parent) as BindHolder<NetworkItem>
            3 -> SerialsViewHolder(parent) as BindHolder<NetworkItem>
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: BindHolder<NetworkItem>, position: Int) {
        return when(getItemViewType(position)) {
            1 -> (holder as PersonViewHolder).bind(getTList()[position] as PersonResult, position)
            2 -> (holder as MovieViewHolder).bind(getTList()[position] as MovieResult, position)
            3 -> (holder as SerialsViewHolder).bind(getTList()[position] as SerialsResult, position)
            else -> super.onBindViewHolder(holder, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getTList()[position]::class) {
            PersonResult::class -> 1
            MovieResult::class -> 2
            SerialsResult::class -> 3
            else -> 0
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
}