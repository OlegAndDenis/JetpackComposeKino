package com.example.kino.screen.moviefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.example.kino.screen.common.ZoomOutPageTransformer
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.GenresViewHolder
import com.example.kino.adapter.MovieViewHolder
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.MovieAndSerialsFragmentBinding
import com.example.kino.db.model.Genres
import com.example.kino.di.components.FragmentComponent
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.screncontainer.ContainerFragment
import com.example.kino.viewmodel.ViewModelFactory
import javax.inject.Inject

class MovieFragment : BaseFragment() {

    @Inject
    lateinit var mFactory: ViewModelFactory
    private lateinit var mViewModel: MovieViewModel

    private lateinit var mBinding: MovieAndSerialsFragmentBinding

    private val mPopAdapter: CommonAdapter<MovieResult> = CommonAdapter(object : HolderCreator<MovieResult> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<MovieResult> {
            return MovieViewHolder(parent)
        }
    })

    private val mGenAdapter: CommonAdapter<Genres> = CommonAdapter(object : HolderCreator<Genres> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<Genres> {
            return GenresViewHolder(parent)
        }
    })

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
       mBinding.popularity.apply {
           setPageTransformer(ZoomOutPageTransformer())
           adapter = mPopAdapter
           overScrollMode = OVER_SCROLL_NEVER
       }
        mBinding.genres.apply {
            setHasFixedSize(true)
            GridLayoutManager(context, 2).also { this.layoutManager = it }
            isNestedScrollingEnabled = false
            adapter = mGenAdapter
        }
        mBinding.scrollView.apply {
            isSmoothScrollingEnabled = true
            overScrollMode = OVER_SCROLL_NEVER
        }
    }

    private fun setMovie(movie: Movie) {
        mPopAdapter.setTList(movie.result)
    }

    private fun setGenres(genres: List<Genres>) {
        mGenAdapter.setTList(genres)
    }

    override fun getFragmentComponent(): FragmentComponent =
        (activity as ContainerFragment).getActivityComponent().getFragmentComponent()
}