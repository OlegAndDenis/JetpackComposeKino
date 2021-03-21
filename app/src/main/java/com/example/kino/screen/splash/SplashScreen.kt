package com.example.kino.screen.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager.*
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.SplashScreenBinding
import com.example.kino.di.components.FragmentComponent
import com.example.kino.screen.common.SingleActivity
import com.example.kino.screen.common.ViewModelTransaction
import com.example.kino.screen.screncontainer.ContainerFragment
import com.example.kino.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SplashScreen : BaseFragment() {

    private lateinit var mViewModel: SplashViewModel
    private val START_RESULT: String = "START"
    private val NO_CONNECTION_NETWORK = "NO_CONNECTION"
    private val ERROR = "ERROR"

    @Inject
    lateinit var mFactory: ViewModelFactory

    lateinit var binding: SplashScreenBinding
    lateinit var transaction: ViewModelTransaction

    override fun onAttach(context: Context) {
        super.onAttach(context)
        getFragmentComponent().inject(this@SplashScreen)
        mViewModel = ViewModelProvider(this@SplashScreen, mFactory).get(SplashViewModel::class.java)
        transaction = ViewModelProvider(activity!!, mFactory).get(ViewModelTransaction::class.java)
        mViewModel.attachObservable.observe(this@SplashScreen, { startIntent(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SplashScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun getFragmentComponent(): FragmentComponent =
        (activity as SingleActivity).getActivityComponent().getFragmentComponent()


    private fun startIntent(s: String) {
        when (s) {
            START_RESULT -> {
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
}