package com.example.kino.screen.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.*
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import com.example.kino.NavigationUi.*
import com.example.kino.R
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.common.CommonFactory
import com.example.kino.connectoninfo.model.ConnectionType
import com.example.kino.databinding.MovieLayoutBinding
import com.example.kino.db.model.Genres
import com.example.kino.extensions.launchViewWhenStartedBlock
import com.example.kino.extensions.navigateSafe
import com.example.kino.screen.common.*
import com.example.kino.screen.common.model.GenresList
import com.example.kino.screen.common.viewholder.VerticalViewHolder
import com.example.kino.screen.movie.viemodel.MovieViewModel
import kotlinx.coroutines.flow.*

class MovieFragment : BaseFragment() {

    private val viewModel: MovieViewModel by navGraphViewModels(R.id.movie_navigation) { CommonFactory }

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

        launchViewWhenStartedBlock {
            with(viewModel) {
                responseId.collect(::openMovie)
                responseGenresByPosition.collect(::openGenres)
                movieByGenres.collect(::setGenres)
                uiNotification.collect(::uiState)
            }
        }
    }

    private fun uiState(connectionType: ConnectionType) {
        when(connectionType) {
            is ConnectionType.Available -> showUi()
                is ConnectionType.Lost -> hideUi()
            else -> {}
        }
    }

    private fun hideUi() {
        binding.gag.visibility = View.VISIBLE
        binding.movieTopFive.visibility = View.GONE
    }

    private fun showUi() {
        binding.gag.visibility = View.GONE
        binding.movieTopFive.visibility = View.VISIBLE
    }

    private fun setGenres(genres: List<GenresList>) {
        adapter.initTList(genres)
    }

    private fun openMovie(id: String) {
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigateSafe(
                R.id.action__MovieFragment_to_DetailFragment,
                bundleOf(Pair(MOVIE_ID.name, id))
            )
    }

    private fun allTop() {
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigateSafe(R.id.actionAllFragment, bundleOf(Pair(TOP.name, "")))
    }

    private fun openGenres(genres: Genres) {
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigateSafe(R.id.actionAllFragment, bundleOf(Pair(GENRES.name, genres)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.movieTopFive.adapter = null
        binding.root.removeAllViews()
        _binding = null
    }
}