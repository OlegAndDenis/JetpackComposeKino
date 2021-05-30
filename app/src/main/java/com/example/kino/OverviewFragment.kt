package com.example.kino

import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kino.databinding.OverviewLayoutBinding
import com.example.kino.network.model.common.GenresApi
import com.example.kino.network.model.movie.MovieDetail
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.TransactionViewModel

class OverviewFragment : BaseFragment() {

    private var _binding: OverviewLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModelTransaction: TransactionViewModel by activityViewModels { CommonFactory }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = OverviewLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelTransaction.overView.observeView(this::setDate)
    }

    private fun setDate(date: MovieDetail) {
        setGenres(date.genres)
        setImage(date.posterPath)
        setOverview(date.overview)
    }

    private fun setGenres(genres: List<GenresApi>) {
        val builder = StringBuilder()
        genres.forEach { builder.append(it.name).append(", ") }
        val genre = builder.toString().replaceRange(builder.length - 2, builder.length, "")
        binding.genres.text = genre
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
        GlideManage.with(binding.poster).loadImage(path, binding.poster)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViews()
        _binding = null
    }
}