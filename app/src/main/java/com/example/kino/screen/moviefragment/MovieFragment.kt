package com.example.kino.screen.moviefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kino.SplashViewModel
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.MovieViewHolder
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.di.components.FragmentComponent
import com.example.kino.network.model.Movie
import com.example.kino.network.model.MovieResult
import com.example.kino.screncontainer.ContainerFragment
import com.example.kino.viewmodel.ViewModelFactory
import javax.inject.Inject

class MovieFragment : BaseFragment(), HolderCreator<MovieResult> {

    @Inject
    lateinit var mFactory: ViewModelFactory
    private lateinit var mViewModel: MovieViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getFragmentComponent().inject(this@MovieFragment)
        mViewModel = ViewModelProvider(this@MovieFragment, mFactory).get(MovieViewModel::class.java)
        mViewModel.responseMovie.observe(this@MovieFragment, {
            setMovie(it)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun setMovie(movie: Movie) {
        val adapter = CommonAdapter(this)
        adapter.setTList(movie.result)
        mPopularityRecycler.adapter = adapter
    }

    override fun create(parent: ViewGroup, viewType: Int): BindHolder<MovieResult> {
        return MovieViewHolder(parent)
    }

    override fun getFragmentComponent(): FragmentComponent =
        (activity as ContainerFragment).getActivityComponent().getFragmentComponent()
}