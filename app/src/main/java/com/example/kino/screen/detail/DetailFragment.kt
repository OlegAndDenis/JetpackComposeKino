package com.example.kino.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kino.NavigationUi.*
import com.example.kino.common.CommonFactory
import com.example.kino.R
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.DetailLayoutBinding
import com.example.kino.extensions.launchView
import com.example.kino.network.model.common.Backdrops
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.screen.common.*
import com.example.kino.screen.common.viewmodel.TransactionViewModel
import com.example.kino.screen.detail.viewholder.DetailViewHolder
import com.example.kino.screen.detail.viewmodel.DetailViewModel
import kotlinx.coroutines.flow.onEach

class DetailFragment : BaseFragment() {

    private val viewModel: DetailViewModel by viewModels { CommonFactory }

    private var _binding: DetailLayoutBinding? = null
    private val binding get() = _binding!!

    private val adapter = CommonAdapter(object : HolderCreator<NetworkItem> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> =
            DetailViewHolder(parent) {}
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DetailLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        showLoading()
        getData()
        viewModel.responseMovie.onEach { setId(it) }.launchView(viewLifecycleOwner)
        viewModel.mapFragment.onEach {
            createNavHost().navController.navigate(R.id.action_detailFragment_to_tabFragment,
            bundleOf(LIST_FRAGMENT.name to it))
        }.launchView(viewLifecycleOwner)

        binding.posterViews.apply {
            adapter = this@DetailFragment.adapter
            PagerSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun getData() {
        val argument = arguments ?: Bundle.EMPTY
        if (argument.isEmpty) return // Fixme Обработать действие назад
        if (argument.containsKey(MOVIE_ID.name)) {
            val id = argument.getString(MOVIE_ID.name) ?: ""
            viewModel.requestId(id)
        }
    }

    private fun showLoading() {
        binding.tabsInfo.visibility = View.GONE
        binding.collapsingToolbar.visibility = View.GONE
        binding.appbar.visibility = View.GONE
        binding.toolbar.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.tabsInfo.visibility = View.VISIBLE
        binding.collapsingToolbar.visibility = View.VISIBLE
        binding.appbar.visibility = View.VISIBLE
        binding.toolbar.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun setId(movie: MovieDetail) {
        hideLoading()
        binding.toolbar.title = if (movie.title.isNotEmpty()) {
            movie.title
        } else {
            movie.originalTitle
        }
        val paths = when {
            movie.images.backdrops.isNotEmpty() -> {
                movie.images.backdrops.sortedBy { it.voteCount }
            }
            movie.images.posters.isNotEmpty() -> {
                movie.images.posters.sortedBy { it.voteCount }
            }
            else -> {
                listOf(Backdrops(file_path = movie.backdropPath ?: movie.posterPath))
            }
        }
        adapter.setTList(paths)
    }

    private fun createNavHost(): NavHostFragment {
        val navHostFragment = NavHostFragment.create(R.navigation.detail_navigation)
        childFragmentManager.beginTransaction()
            .add(binding.tabsInfo.id, navHostFragment)
            .commitNow()
        return navHostFragment
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).setSupportActionBar(null)
        binding.root.removeAllViews()
        _binding = null
    }
}