package com.example.kino.screen.moviefragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.*
import com.example.kino.CommonFactory
import com.example.kino.screen.common.ZoomOutPageTransformer
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.screen.common.GenresViewHolder
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.MovieAndSerialsFragmentBinding
import com.example.kino.db.model.Genres
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.Movie
import com.example.kino.screen.common.SingleActivity

class MovieFragment : BaseFragment() {

    private val mViewModel: MovieViewModel by viewModels { CommonFactory }

    private var _binding: MovieAndSerialsFragmentBinding? = null
    private val binding get() = _binding!!

    private val mPopAdapter: CommonAdapter<NetworkItem> = CommonAdapter(object : HolderCreator<NetworkItem> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> {
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
        mViewModel.responseMovie.observe(this@MovieFragment, this::setMovie)
        mViewModel.responseGenres.observe(this@MovieFragment, this::setGenres)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieAndSerialsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.popularity.apply {
           setPageTransformer(ZoomOutPageTransformer())
           adapter = mPopAdapter
           overScrollMode = OVER_SCROLL_NEVER
       }
        binding.genres.apply {
            setHasFixedSize(true)
            GridLayoutManager(context, 2).also { this.layoutManager = it }
            isNestedScrollingEnabled = false
            adapter = mGenAdapter
        }
        binding.scrollView.apply {
            isSmoothScrollingEnabled = true
            overScrollMode = OVER_SCROLL_NEVER
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.genres.adapter = null
        binding.popularity.adapter = null
        _binding = null
    }

    private fun setMovie(movie: Movie) = mPopAdapter.setTList(movie.result)

    private fun setGenres(genres: List<Genres>) = mGenAdapter.setTList(genres)
}