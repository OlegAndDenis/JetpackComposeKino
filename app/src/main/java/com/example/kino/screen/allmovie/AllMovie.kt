package com.example.kino.screen.allmovie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.kino.CommonFactory
import com.example.kino.OldAndNewList
import com.example.kino.OnScrollListener
import com.example.kino.OnVerticalScrollListener
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.DataDiffUtils
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.databinding.AllMovieLayoutBinding
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.CommonNavigation
import com.example.kino.screen.common.TransactionViewModel

class AllMovie : BaseFragment(), OnVerticalScrollListener {

    private var _binding: AllMovieLayoutBinding? = null
    private val binding get() = _binding!!
    private var navigation: CommonNavigation? = null

    private val viewModelTransaction: TransactionViewModel by activityViewModels { CommonFactory }
    private val viewModel: AllMovieViewModel by viewModels { CommonFactory }

    private val adapter = CommonAdapter(object : HolderCreator<NetworkItem> {
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> {
            return AllViewHolder(parent) {}
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
        activity?.let { navigation = it as CommonNavigation }
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        viewModelTransaction.responseTop.observeView { showTop() }
        viewModelTransaction.responseGenres.observeView { showGenres() }
        binding.recyclerView.apply {
            adapter = this@AllMovie.adapter
            LinearSnapHelper().attachToRecyclerView(this)
            addOnScrollListener(OnScrollListener(this@AllMovie))
        }
    }

    private fun showTop() {
        viewModel.responseMovieResult.observeView(this::setPopularity)
        binding.toolbar.title = "Популярные"
        viewModel.newTopPage()
    }

    private fun showGenres() {
        Log.i("OLEG","this")
    }

    private fun setPopularity(oldAndNewList: OldAndNewList) {
        val diffUtils = DataDiffUtils(oldAndNewList.old, oldAndNewList.new)
        val diffUtilsResult = DiffUtil.calculateDiff(diffUtils)
        adapter.setTList(oldAndNewList.new)
        diffUtilsResult.dispatchUpdatesTo(adapter)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        navigation = null
        binding.root.removeAllViews()
        _binding = null
    }

    override fun onScrolledToTop() {

    }

    override fun onScrolledToBottom() {
        viewModel.newTopPage()
    }

    override fun onScrolledUp() {

    }

    override fun onScrolledDown() {

    }
}