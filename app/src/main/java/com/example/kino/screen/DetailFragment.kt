package com.example.kino.screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.kino.CommonFactory
import com.example.kino.databinding.DetailLayoutBinding
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.TransactionViewModel

class DetailFragment : BaseFragment() {

    private val viewModelTransaction: TransactionViewModel by activityViewModels { CommonFactory }

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
        viewModelTransaction.responseId.observe(viewLifecycleOwner, this::setId)
    }

    private fun setId(string: String) {
        Log.i("OLEG", string)
        binding.text.text = string
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}