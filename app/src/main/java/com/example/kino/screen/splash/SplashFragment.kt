package com.example.kino.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.kino.common.CommonFactory
import com.example.kino.R
import com.example.kino.databinding.SplashLayoutBinding
import com.example.kino.extensions.findNavController
import com.example.kino.extensions.launchView
import com.example.kino.extensions.navigateSafe
import com.example.kino.network.NetworkEnum
import com.example.kino.network.NetworkEnum.*
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.splash.viewmodel.SplashViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.onEach

class SplashFragment : BaseFragment() {

    private var _binding: SplashLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels { CommonFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SplashLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.notificationUi.onEach(this::statusNetwork).launchView(viewLifecycleOwner)
    }

    private fun statusNetwork(status: NetworkEnum) {
        when (status) {
            OK -> {
                findNavController()
                    .navigateSafe(R.id.action_splashFragment_to_navigationFragment)
            }
            NO_CONNECTION -> noConnection()
            ERROR -> requireActivity().finish()
        }
    }

    private fun noConnection() {
        Snackbar.make(
            binding.root.context,
            binding.root,
            "Проверте интернет подключение!",
            Snackbar.LENGTH_LONG
        ).setAction("Повторить") {
            viewModel.restart()
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViews()
        _binding = null
    }
}