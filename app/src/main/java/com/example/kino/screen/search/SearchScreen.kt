package com.example.kino.screen.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.SearchScreenBinding
import com.example.kino.di.components.FragmentComponent

class SearchScreen: BaseFragment() {

    private lateinit var binding: SearchScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun getFragmentComponent(): FragmentComponent {
        TODO("Not yet implemented")
    }

}