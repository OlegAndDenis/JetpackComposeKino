package com.example.kino.screen.movie

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kino.CommonFactory
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.adapter.holder.ClickItem
import com.example.kino.databinding.MovieLayoutBinding
import com.example.kino.db.model.Genres
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.screen.GenresViewHolder
import com.example.kino.screen.IndicatorDecoration
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.CommonNavigation

class MovieFragment : BaseFragment() {

    private val viewModel: MovieViewModel by viewModels { CommonFactory }

    private var _binding: MovieLayoutBinding? = null
    private val binding get() = _binding!!

    private var navigation: CommonNavigation? = null

    private val topFiveAdapter = CommonAdapter(object : HolderCreator<MovieResult> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<MovieResult> {
            return if (viewType == 1) MovieAllViewHolder(parent) {
                this@MovieFragment.selectedItem(it)
            }
            else MovieViewHolder(parent) { this@MovieFragment.selectedItem(it) }
        }
    })

    private val genresAdapter = CommonAdapter(object : HolderCreator<Genres> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<Genres> {
            return GenresViewHolder(parent)
        }
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MovieLayoutBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let { navigation = it as CommonNavigation }
        binding.movieTopFive.apply {
            adapter = topFiveAdapter
            addItemDecoration(IndicatorDecoration())
            PagerSnapHelper().attachToRecyclerView(this)
        }
        binding.movieGenres.apply {
            adapter = genresAdapter
            setHasFixedSize(true)
        }
        viewModel.responseMovie.observe(viewLifecycleOwner, this::setTopFive)
        viewModel.responseGenres.observe(viewLifecycleOwner, this::setGenres)
    }

    private fun setTopFive(movie: List<MovieResult>) {
        topFiveAdapter.setTList(movie)
        binding.movieTopFive.scrollToPosition(2)
    }

    private fun setGenres(genres: List<Genres>) {
        genresAdapter.setTList(genres)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        navigation = null
    }

    private fun selectedItem(position: Int) {
        viewModel.getMovieClick(position)
    }
}