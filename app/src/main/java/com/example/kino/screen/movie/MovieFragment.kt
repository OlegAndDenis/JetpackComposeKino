package com.example.kino.screen.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.databinding.MovieLayoutBinding
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.CommonNavigation
import com.example.kino.screen.common.ContainerId.*
import com.example.kino.screen.common.ScreenEnum.*

class MovieFragment : BaseFragment() {

    private var _binding: MovieLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var navigation: CommonNavigation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = MovieLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let { navigation = it as CommonNavigation}
        binding.movieGenresText.setOnClickListener {
            navigation.openScreen(SPLASH, GLOBAL_FRAME)
        }
    }
}