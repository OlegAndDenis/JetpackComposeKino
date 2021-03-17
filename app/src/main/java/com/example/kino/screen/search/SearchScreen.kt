package com.example.kino.screen.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.SearchScreenBinding
import com.example.kino.di.components.FragmentComponent
import com.example.kino.screen.moviefragment.MovieViewModel
import com.example.kino.screen.screncontainer.ContainerFragment
import com.example.kino.screen.screncontainer.ContainerViewModel
import com.example.kino.viewmodel.ViewModelFactory
import javax.inject.Inject

class SearchScreen: BaseFragment() {

    private lateinit var binding: SearchScreenBinding

    @Inject
    lateinit var mFactory: ViewModelFactory

    private lateinit var mViewModel: ContainerViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getFragmentComponent().inject(this@SearchScreen)
        mViewModel = ViewModelProvider(activity!!, mFactory).get(ContainerViewModel::class.java)
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
        mViewModel.responseSearch.observe(this@SearchScreen, { Log.i("OLEG", "$it")})
    }

    override fun getFragmentComponent(): FragmentComponent =
        (activity as ContainerFragment).getActivityComponent().getFragmentComponent()

}