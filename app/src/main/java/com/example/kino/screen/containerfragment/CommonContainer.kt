package com.example.kino.screen.containerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.kino.databinding.ContainerLayoutBinding
import com.example.kino.screen.common.BaseFragment
import timber.log.Timber

class CommonContainer: BaseFragment() {

    private var _binding: ContainerLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ContainerLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("Common")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}