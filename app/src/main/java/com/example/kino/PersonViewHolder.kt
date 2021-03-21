package com.example.kino

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.network.model.person.PersonResult

class PersonViewHolder private constructor(itemView: View) : BindHolder<PersonResult>(itemView) {

    constructor(parent: ViewGroup) : this(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.popularity_view, parent, false))

    override fun bind(item: PersonResult, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

}