package com.example.kino.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.kino.CommonFactory
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.DetailLayoutBinding
import com.example.kino.network.model.common.Backdrops
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.screen.common.*

class DetailFragment : BaseFragment() {

    private val viewModelTransaction: TransactionViewModel by activityViewModels { CommonFactory }
    private val viewModel: DetailViewModel by viewModels { CommonFactory }
    private var navigation: CommonNavigation? = null

    private var _binding: DetailLayoutBinding? = null
    private val binding get() = _binding!!

    private val adapter = CommonAdapter(object : HolderCreator<NetworkItem> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> =
            DetailViewHolder(parent) {}
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DetailLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModelTransaction.responseId.observeView(viewModel::requestId)
        viewModel.responseMovie.observeView { setId(it) }
        viewModel.mapFragment.observeView(viewModelTransaction::callFragment)
        viewModel.overview.observeView(viewModelTransaction::callOverView)

        binding.posterViews.apply {
            adapter = this@DetailFragment.adapter
            PagerSnapHelper().attachToRecyclerView(this)
        }
        navigation?.openScreen(ScreenEnum.TABS, ContainerId.TAB)
    }

    private fun setId(movie: MovieDetail) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        navigation = null
        (activity as AppCompatActivity).setSupportActionBar(null)
        binding.root.removeAllViews()
        _binding = null
    }
}