package com.example.kino.screen.pager.overview

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.kino.R
import com.example.kino.common.CommonFactory
import com.example.kino.databinding.OverviewLayoutBinding
import com.example.kino.db.model.Genres
import com.example.kino.glide.GlideManage
import com.example.kino.extensions.launchView
import com.example.kino.network.model.common.GenresApi
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.viewmodel.TransactionViewModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.onEach

class OverviewFragment : BaseFragment() {

    private var _binding: OverviewLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModelTransaction: TransactionViewModel by activityViewModels { CommonFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = OverviewLayoutBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelTransaction.overView.onEach(this::setDate).launchView(viewLifecycleOwner)
    }

    private fun setDate(date: MovieDetail) {
        setGenres(date.genres)
        setImage(date.posterPath)
        setOverview(date.overview)
    }

    private fun setGenres(genres: List<GenresApi>) {
        binding.genres.setGenres(genres)
        binding.genres.setOnClickListener {
            val bundle = Bundle()
            val json = Gson().toJson(Genres(idGenres = it.id, name = it.name))
            bundle.putString("genresId", json)
            Navigation.findNavController(requireActivity(), R.id.common_frame)
                .navigate(R.id.action_overviewFragment_to_all_navigation, bundle)
        }
    }

    private fun setOverview(overview: String) {
        val shader =
            LinearGradient(0F, 200F, 0F, 0F, Color.WHITE, Color.BLACK, Shader.TileMode.CLAMP)
        binding.overview.paint.shader = shader
        binding.overview.text = overview
        binding.overview.setOnClickListener {
            if (binding.overview.maxLines == Int.MAX_VALUE) {
                binding.overview.paint.shader = shader
                binding.overview.maxLines = 3
            } else {
                binding.overview.paint.shader = null
                binding.overview.maxLines = Int.MAX_VALUE
            }
        }
    }

    private fun setImage(path: String) {
        GlideManage.with(binding.poster).loadRoundPoster(path, binding.poster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.genres.setOnClickListener(null)
        binding.genres.removeAllViews()
        binding.genres.onDestroy()
        binding.root.removeAllViews()
        _binding = null
    }
}