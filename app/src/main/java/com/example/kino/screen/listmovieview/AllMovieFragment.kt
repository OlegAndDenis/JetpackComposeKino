package com.example.kino.screen.listmovieview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.kino.NavigationUi.*
import com.example.kino.common.CommonFactory
import com.example.kino.R
import com.example.kino.screen.movie.model.OldAndNewList
import com.example.kino.listener.OnScrollListener
import com.example.kino.listener.OnVerticalScrollListener
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.DataDiffUtils
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.AllMovieLayoutBinding
import com.example.kino.db.model.Genres
import com.example.kino.extensions.launchView
import com.example.kino.extensions.navigateSafe
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.screen.listmovieview.viewholder.AllViewHolder
import com.example.kino.screen.listmovieview.viewmodel.AllMovieViewModel
import com.example.kino.screen.common.*
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class AllMovieFragment : BaseFragment(), OnVerticalScrollListener {

    private var _binding: AllMovieLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AllMovieViewModel by navGraphViewModels(R.id.all_movie_navigation) { CommonFactory }

    private val adapter = CommonAdapter(object : HolderCreator<NetworkItem> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> {
            return AllViewHolder(parent, viewModel::getMovieClick)
        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = AllMovieLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        viewModel.responseMovieResult.onEach(this::setPopularity).launchView(viewLifecycleOwner)
        viewModel.resultId.onEach(this::openMovie).launchView(viewLifecycleOwner)
        viewModel.title.onEach { binding.toolbar.title = it }.launchView(viewLifecycleOwner)
        binding.recyclerView.apply {
            adapter = this@AllMovieFragment.adapter
            LinearSnapHelper().attachToRecyclerView(this)
            addOnScrollListener(OnScrollListener(this@AllMovieFragment))
        }
    }

    private fun getData() {
        val argument = arguments ?: Bundle.EMPTY
        if (argument.isEmpty) return // Fixme Обработать действие назад
        lifecycleScope.launchWhenStarted {
            if (argument.containsKey(GENRES.name)) {
                val genres = (argument.getParcelable(GENRES.name) ?: Genres())
                showGenres(genres)
            } else if (argument.containsKey(TOP.name)) {
                showTop()
            }
        }
    }

    private fun showTop() {
        binding.toolbar.title = "популярные"
        viewModel.newPage()
        viewModel.setTitle("популярные")
    }

    private fun showGenres(genres: Genres) {
        viewModel.setGenres(genres.idGenres.toString())
        binding.toolbar.title = genres.name
        viewModel.newPage()
        viewModel.setTitle(genres.name)
    }

    private fun setPopularity(oldAndNewList: OldAndNewList) {
        val diffUtils = DataDiffUtils(oldAndNewList.old, oldAndNewList.new)
        val diffUtilsResult = DiffUtil.calculateDiff(diffUtils)
        adapter.setTList(oldAndNewList.new)
        diffUtilsResult.dispatchUpdatesTo(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViews()
        _binding = null
    }

    private fun openMovie(id: String) {
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigateSafe(
                R.id.action_all_movie_navigation_to_detail_navigation,
                bundleOf(MOVIE_ID.name to id)
            )
    }

    override fun onScrolledToTop() {

    }

    override fun onScrolledToBottom() {
        viewModel.newPage()
    }

    override fun onScrolledUp() {

    }

    override fun onScrolledDown() {

    }
}