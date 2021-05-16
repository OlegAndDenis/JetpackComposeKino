package com.example.kino.screen.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kino.CommonFactory
import com.example.kino.R
import com.example.kino.VerticalViewHolder
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.MovieLayoutBinding
import com.example.kino.db.model.Genres
import com.example.kino.findNavController
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.screen.GenresList
import com.example.kino.screen.IndicatorDecoration
import com.example.kino.screen.common.*
import com.example.kino.screen.common.ContainerId.*
import com.example.kino.screen.common.ScreenEnum.*

private const val SCROLL_TO_POSITION = 2
private const val MOVIE_ALL_TYPE = 1

class MovieFragment : BaseFragment() {

    private val viewModel: MovieViewModel by viewModels { CommonFactory }
    private val viewModelTransaction: TransactionViewModel by activityViewModels { CommonFactory }

    private var _binding: MovieLayoutBinding? = null
    private val binding get() = _binding!!


    private val adapter = CommonAdapter(object : HolderCreator<GenresList> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<GenresList> {
            return VerticalViewHolder(parent, viewModel::getMovieClick)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.movieTopFive.apply {
            adapter = this@MovieFragment.adapter
        }
        viewModel.movieByGenres.observe(viewLifecycleOwner, this::setGenres)
        viewModel.responseId.observe(viewLifecycleOwner, this::openMovie)
        viewModel.responseGenresByPosition.observeView(this::openGenres)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViews()
        binding.movieTopFive.adapter = null
        _binding = null
    }

    private fun setGenres(genres: List<GenresList>) {
        adapter.setTList(genres)
    }

    private fun openMovie(id: String) {
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigate(R.id.action__MovieFragment_to_DetailFragment)
        viewModelTransaction.callId(id)
    }

    private fun allTop() {
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigate(R.id.open_MovieFragment_to_AllFragment)
        viewModelTransaction.callTop()
    }

    private fun openGenres(genres: Genres) {
        viewModelTransaction.callGenres(genres)
    }
}