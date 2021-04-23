package com.example.kino.screen.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.kino.CommonFactory
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.SearchScreenBinding
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.screen.common.SingleActivity
import com.example.kino.screen.screncontainer.ContainerViewModel

class SearchFragment : BaseFragment() {

    private lateinit var binding: SearchScreenBinding

    private val mViewModel: ContainerViewModel by viewModels { CommonFactory }

    private val adapterSearch = ExpandableAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mViewModel.search.observe(this@SearchFragment, this::setMovie)
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
//        adapterSearch.setType("search")
        binding.expandableList.setAdapter(adapterSearch)
    }

    private fun setMovie(map: Map<NetworkItem, List<NetworkItem>>) {
        adapterSearch.setMap(map)
//        diffUtils(adapterSearch.getTList(), searchResult.result)
    }
//
//    private fun diffUtils(oldItems: List<NetworkItem>, nextItems: List<NetworkItem>) {
//        val tDiffUtil: DataDiffUtils<NetworkItem> = DataDiffUtils(oldItems, nextItems)
//        val result = DiffUtil.calculateDiff(tDiffUtil)
//        adapterSearch.setTList(nextItems)
//        result.dispatchUpdatesTo(adapterSearch)
//    }
}