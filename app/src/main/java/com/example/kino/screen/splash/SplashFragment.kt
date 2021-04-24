package com.example.kino.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.kino.CommonFactory
import com.example.kino.SplashViewModel
import com.example.kino.databinding.SplashLayoutBinding
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.CommonNavigation
import com.example.kino.screen.common.ScreenEnum
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

private const val START_RESULT: String = "START"
private const val NO_CONNECTION_NETWORK = "NO_CONNECTION"
private const val ERROR = "ERROR"

class SplashFragment : BaseFragment() {

    private var _binding: SplashLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SplashViewModel by viewModels { CommonFactory }

    private var _navigation: CommonNavigation? = null
    private val navigation get() = _navigation!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashLayoutBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity.let { _navigation = it as CommonNavigation }
        viewModel.attachObservable.observe(viewLifecycleOwner, this::startIntent)
    }

    private fun startIntent(s: String) {
        when (s) {
            START_RESULT -> navigation.openScreen(ScreenEnum.COMMONVIEW)
            NO_CONNECTION_NETWORK -> {
                val snackbar: Snackbar = Snackbar.make(
                    binding.root,
                    "Проверте интернет подключение!",
                    BaseTransientBottomBar.LENGTH_INDEFINITE
                )
                snackbar.setAction("Повторить") {
                    viewModel.restart()
                    snackbar.dismiss()
                }
                snackbar.show()
            }
            ERROR -> requireActivity().finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _navigation = null
        _binding = null
    }
}