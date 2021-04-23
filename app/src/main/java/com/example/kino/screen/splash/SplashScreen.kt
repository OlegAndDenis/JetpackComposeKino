package com.example.kino.screen.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.work.WorkManager.*
import com.example.kino.CommonFactory
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.SplashScreenBinding
import com.example.kino.screen.common.SingleActivity
import com.example.kino.screen.common.ViewModelTransaction
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber

class SplashScreen : BaseFragment() {

    private val mViewModel: SplashViewModel by viewModels { CommonFactory }
    private val START_RESULT: String = "START"
    private val NO_CONNECTION_NETWORK = "NO_CONNECTION"
    private val ERROR = "ERROR"

    private var _binding: SplashScreenBinding? = null
    private val binding get() =  _binding!!
    val transaction: ViewModelTransaction by viewModels { CommonFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.let { mViewModel.attachObservable.observe(it, this::startIntent) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


    private fun startIntent(s: String) {
        when (s) {
            START_RESULT -> {
                Timber.i("++++")
                transaction.transaction("globalMenu")
                onDestroy()
            }
            NO_CONNECTION_NETWORK -> {
                val snackbar: Snackbar = Snackbar.make(
                    binding.root,
                    "Проверте интернет подключение!",
                    BaseTransientBottomBar.LENGTH_INDEFINITE
                )
                snackbar.setAction("Повторить") {
                    mViewModel.restart()
                    snackbar.dismiss()
                }
                snackbar.show()
            }
            ERROR -> {
                (activity as SingleActivity).finish()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}