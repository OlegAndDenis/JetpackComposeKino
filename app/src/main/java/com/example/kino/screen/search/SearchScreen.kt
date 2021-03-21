package com.example.kino.screen.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kino.PersonViewHolder
import com.example.kino.SearchAdapter
import com.example.kino.SearchViewHolder
import com.example.kino.adapter.CommonAdapter
import com.example.kino.adapter.CommonAdapter.*
import com.example.kino.adapter.holder.BindHolder
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.SearchScreenBinding
import com.example.kino.di.components.FragmentComponent
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.network.model.movie.Movie
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.network.model.search.SearchResult
import com.example.kino.screen.common.SingleActivity
import com.example.kino.screen.moviefragment.MovieViewHolder
import com.example.kino.screen.moviefragment.MovieViewModel
import com.example.kino.screen.screncontainer.ContainerFragment
import com.example.kino.screen.screncontainer.ContainerViewModel
import com.example.kino.screen.serialfragment.SerialsViewHolder
import com.example.kino.viewmodel.ViewModelFactory
import javax.inject.Inject

class SearchScreen: BaseFragment() {

    private lateinit var binding: SearchScreenBinding

    @Inject
    lateinit var mFactory: ViewModelFactory

    private lateinit var mViewModel: ContainerViewModel
    private val adapterSearch: SearchAdapter = SearchAdapter(object : HolderCreator<NetworkItem>{
        override fun create(parent: ViewGroup, viewType: Int): BindHolder<NetworkItem> {
           return SearchViewHolder(parent)
        }
    })

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getFragmentComponent().inject(this@SearchScreen)
        mViewModel = ViewModelProvider(activity!!, mFactory).get(ContainerViewModel::class.java)
        mViewModel.responseSearch.observe(this@SearchScreen, this::setMovie)
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
        binding.recycler.apply {
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = adapterSearch
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }

    private fun setMovie(searchResult: SearchResult) {
        adapterSearch.setTList(searchResult.result)
    }

    override fun getFragmentComponent(): FragmentComponent =
        (activity as SingleActivity).getActivityComponent().getFragmentComponent()

}