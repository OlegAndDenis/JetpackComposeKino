package com.example.kino.screen.moviefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import androidx.viewpager2.widget.ViewPager2
import com.example.kino.ZoomOutPageTransformer
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.GenresViewHolder
import com.example.kino.adapter.MovieViewHolder
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.MovieAndSerialsFragmentBinding
import com.example.kino.db.model.Genres
import com.example.kino.di.components.FragmentComponent
import com.example.kino.network.model.Movie
import com.example.kino.network.model.MovieResult
import com.example.kino.screncontainer.ContainerFragment
import com.example.kino.viewmodel.ViewModelFactory
import javax.inject.Inject

class MovieFragment : BaseFragment() {

    @Inject
    lateinit var mFactory: ViewModelFactory
    private lateinit var mViewModel: MovieViewModel

    private lateinit var mBinding: MovieAndSerialsFragmentBinding

    private lateinit var mPopularity: ViewPager2
    private lateinit var mGenresRecycler: RecyclerView

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getFragmentComponent().inject(this@MovieFragment)
        mViewModel = ViewModelProvider(this@MovieFragment, mFactory).get(MovieViewModel::class.java)
        mViewModel.responseMovie.observe(this@MovieFragment, this::setMovie)
        mViewModel.responseGenres.observe(this@MovieFragment, this::setGenres)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = MovieAndSerialsFragmentBinding.inflate(inflater, container, false)
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mPopularity = mBinding.popularity
        mPopularity.setPageTransformer(ZoomOutPageTransformer())
        mGenresRecycler = mBinding.genres
        mGenresRecycler.setHasFixedSize(true)
        mGenresRecycler.layoutManager = GridLayoutManager(context, 2)
        ViewCompat.setNestedScrollingEnabled(mGenresRecycler, false)
    }

    private fun setMovie(movie: Movie) {
        val adapter = CommonAdapter(object : HolderCreator<MovieResult> {
            override fun create(parent: ViewGroup, viewType: Int): BindHolder<MovieResult> {
                return MovieViewHolder(parent)
            }
        })
        adapter.setTList(movie.result)
        mPopularity.adapter = adapter
    }

    private fun setGenres(genres: List<Genres>) {
        val adapter = CommonAdapter(object : HolderCreator<Genres> {
            override fun create(parent: ViewGroup, viewType: Int): BindHolder<Genres> {
                return GenresViewHolder(parent)
            }
        })
        adapter.setTList(genres)
        mGenresRecycler.adapter = adapter
    }

    override fun getFragmentComponent(): FragmentComponent =
        (activity as ContainerFragment).getActivityComponent().getFragmentComponent()
}