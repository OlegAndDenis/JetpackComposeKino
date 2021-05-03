package com.example.kino.screen.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.kino.CommonFactory
import com.example.kino.databinding.DetailLayoutBinding
import com.example.kino.network.model.movie.MovieResult
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.TransactionViewModel

class DetailFragment : BaseFragment() {

    private val viewModelTransaction: TransactionViewModel by activityViewModels { CommonFactory }
    private val viewModel: DetailViewModel by viewModels { CommonFactory }

    private var _binding: DetailLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DetailLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModelTransaction.responseId.observeView(viewModel::requestId)
        viewModel.responseMovie.observeView { setId(it) }
    }

    private fun setId(movie: MovieResult) {
        binding.text.text = movie.title
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}