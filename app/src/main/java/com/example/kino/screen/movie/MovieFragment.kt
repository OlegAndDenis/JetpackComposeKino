package com.example.kino.screen.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.kino.common.CommonFactory
import com.example.kino.R
import com.example.kino.screen.common.viewholder.VerticalViewHolder
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.MovieLayoutBinding
import com.example.kino.db.model.Genres
import com.example.kino.extensions.launchView
import com.example.kino.screen.common.model.GenresList
import com.example.kino.screen.common.*
import com.example.kino.screen.movie.viemodel.MovieViewModel
import kotlinx.coroutines.flow.*
import timber.log.Timber

class MovieFragment : BaseFragment() {

    private val viewModel: MovieViewModel by viewModels { CommonFactory }

    private var _binding: MovieLayoutBinding? = null
    private val binding get() = _binding!!


    private val adapter = CommonAdapter(object : HolderCreator<GenresList> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<GenresList> {
            return VerticalViewHolder(parent, viewModel::getMovieClick, viewModel::clickByCategory)
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

        viewModel.responseId.onEach(this::openMovie).launchView(viewLifecycleOwner)
        viewModel.responseGenresByPosition.onEach(this::openGenres).launchView(viewLifecycleOwner)
        viewModel.movieByGenres.onEach(this@MovieFragment::setGenres).launchView(viewLifecycleOwner)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViews()
        binding.movieTopFive.adapter = null
        _binding = null
    }

    private fun setGenres(genres: List<GenresList>) {
        Timber.i("$genres")
        adapter.setTList(genres)
    }

    private fun openMovie(id: String) {
        val bundle = bundleOf(Pair("idMovie", id))
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigate(R.id.action__MovieFragment_to_DetailFragment, bundle)
    }

    private fun allTop() {
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigate(R.id.open_MovieFragment_to_AllFragment)
    }

    private fun openGenres(genres: Genres) {
        val bundle = bundleOf("genresId" to genres)
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigate(R.id.open_MovieFragment_to_AllFragment, bundle)
    }
}