package com.example.kino.screen.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.DataDiffUtils
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.SearchScreenBinding
import com.example.kino.di.components.FragmentComponent
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.search.SearchResult
import com.example.kino.screen.common.SingleActivity
import com.example.kino.screen.screncontainer.ContainerViewModel
import com.example.kino.viewmodel.ViewModelFactory
import javax.inject.Inject

class SearchFragment : BaseFragment() {

    private lateinit var binding: SearchScreenBinding

    @Inject
    lateinit var mFactory: ViewModelFactory

    private lateinit var mViewModel: ContainerViewModel

    private val adapterSearch = SearchAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getFragmentComponent().inject(this@SearchFragment)
        mViewModel = ViewModelProvider(activity!!, mFactory).get(ContainerViewModel::class.java)
        mViewModel.responseSearch.observe(this@SearchFragment, this::setMovie)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapterSearch.setType("search")
        binding.recyclerSearch.apply {
            LinearLayoutManager(context, RecyclerView.VERTICAL, false).also { this.layoutManager = it }
            adapter = adapterSearch
        }
    }

    private fun setMovie(searchResult: SearchResult) {
        diffUtils(adapterSearch.getTList(), searchResult.result)
    }

    private fun diffUtils(oldItems: List<NetworkItem>, nextItems: List<NetworkItem>) {
        val tDiffUtil: DataDiffUtils<NetworkItem> = DataDiffUtils(oldItems, nextItems)
        val result = DiffUtil.calculateDiff(tDiffUtil)
        adapterSearch.setTList(nextItems)
        result.dispatchUpdatesTo(adapterSearch)
    }

    override fun getFragmentComponent(): FragmentComponent =
        (activity as SingleActivity).getActivityComponent().getFragmentComponent()

}