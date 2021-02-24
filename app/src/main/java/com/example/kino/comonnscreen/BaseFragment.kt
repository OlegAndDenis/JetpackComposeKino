package com.example.kino.comonnscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.databinding.MovieAndSerialsFragmentBinding
import com.example.kino.di.components.FragmentComponent

abstract class BaseFragment : Fragment() {

    protected lateinit var binding : MovieAndSerialsFragmentBinding

    protected lateinit var mPopularityRecycler: RecyclerView
    protected lateinit var mGenresRecycler: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = MovieAndSerialsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPopularityRecycler = binding.popularity
        mGenresRecycler = binding.genres
    }

    abstract fun getFragmentComponent() : FragmentComponent
}