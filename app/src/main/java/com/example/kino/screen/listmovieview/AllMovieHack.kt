package com.example.kino.screen.listmovieview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearSnapHelper
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
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.screen.listmovieview.viewholder.AllViewHolder
import com.example.kino.screen.listmovieview.viewmodel.AllMovieViewModel
import com.example.kino.screen.common.*
import com.example.kino.screen.common.viewmodel.TransactionViewModel
import kotlinx.coroutines.flow.onEach
import timber.log.Timber

class AllMovieHack : BaseFragment(), OnVerticalScrollListener {

    private var _binding: AllMovieLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModelTransaction: TransactionViewModel by activityViewModels { CommonFactory }
    private val viewModel: AllMovieViewModel by viewModels { CommonFactory }

    private val adapter = CommonAdapter(object : HolderCreator<NetworkItem> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> {
            return AllViewHolder(parent, viewModel::getMovieClick)
        }
    })

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
        viewModelTransaction.responseTop.onEach { showTop() }.launchView(viewLifecycleOwner)
        viewModelTransaction.responseGenres.onEach(this::showGenres).launchView(viewLifecycleOwner)
        viewModel.responseMovieResult.onEach(this::setPopularity).launchView(viewLifecycleOwner)
        viewModel.resultId.onEach(this::openMovie).launchView(viewLifecycleOwner)
        viewModel.titleHack.onEach{ binding.toolbar.title = it }.launchView(viewLifecycleOwner)
        binding.recyclerView.apply {
            adapter = this@AllMovieHack.adapter
            LinearSnapHelper().attachToRecyclerView(this)
            addOnScrollListener(OnScrollListener(this@AllMovieHack))
        }
    }

    private fun showTop() {
        Timber.i("THIS")
//        binding.toolbar.title = "популярные"
//        viewModel.newPage()
//        viewModel.setTitle("популярные")
    }

    private fun showGenres(genres: Genres) {
        viewModel.setGenres(genres.idGenres.toString())
        binding.toolbar.title = genres.name
        viewModel.newPage()
        viewModel.setTitleForHack(genres.name)
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
        viewModelTransaction.callId(id)
        Navigation.findNavController(requireActivity(), R.id.common_frame)
            .navigate(R.id.action_all_movie_navigation_to_detail_navigation)
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