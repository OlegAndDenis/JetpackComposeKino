package com.example.kino.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.kino.CommonFactory
import com.example.kino.databinding.SplashLayoutBinding
import com.example.kino.network.NetworkEnum
import com.example.kino.network.NetworkEnum.*
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.CommonNavigation
import com.example.kino.screen.common.ContainerId
import com.example.kino.screen.common.ContainerId.*
import com.example.kino.screen.common.ScreenEnum
import com.example.kino.screen.common.ScreenEnum.*
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

class SplashFragment : BaseFragment() {

    private var _binding: SplashLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels { CommonFactory }

    private var _navigation: CommonNavigation? = null
    private val navigation get() = _navigation!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = SplashLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity.let { _navigation = it as CommonNavigation }
        viewModel.attachObservable.observeView(this::statusNetwork)
    }

    private fun statusNetwork(status: NetworkEnum) {
        when (status) {
            OK -> navigation.openScreen(COMMONVIEW, GLOBAL_FRAME)
            NO_CONNECTION -> noConnection()
            ERROR -> requireActivity().finish()
        }
    }

    private fun noConnection() {
        Snackbar.make(
            binding.root,
            "Проверте интернет подключение!",
            BaseTransientBottomBar.LENGTH_INDEFINITE
        ).setAction("Повторить") {
            viewModel.restart()
        }.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.root.removeAllViewsInLayout()
        _navigation = null
        _binding = null
    }
}