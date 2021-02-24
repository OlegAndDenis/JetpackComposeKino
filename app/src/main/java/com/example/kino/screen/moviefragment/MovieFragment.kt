package com.example.kino.screen.moviefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.di.components.FragmentComponent
import com.example.kino.screncontainer.ContainerFragment

class MovieFragment : BaseFragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getFragmentComponent().inject(this@MovieFragment)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun getFragmentComponent(): FragmentComponent =
        (activity as ContainerFragment).getActivityComponent().getFragmentComponent()
}