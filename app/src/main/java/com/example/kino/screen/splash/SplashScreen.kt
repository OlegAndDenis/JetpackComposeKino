package com.example.kino.screen.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkManager.*
import com.example.kino.applicationm.MovieApplication
import com.example.kino.comonnscreen.Base
import com.example.kino.databinding.SplashScreenBinding
import com.example.kino.di.components.ActivityComponent
import com.example.kino.di.moduls.ActivityModule
import com.example.kino.screncontainer.ContainerFragment
import com.example.kino.viewmodel.ViewModelFactory
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SplashScreen : Base() {

    private lateinit var mViewModel: SplashViewModel
    private val START_RESULT: String = "START"
    private val NO_CONNECTION_NETWORK = "NO_CONNECTION"
    private val ERROR = "ERROR"

    @Inject
    lateinit var mFactory: ViewModelFactory

    lateinit var binding: SplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        getActivityComponent().inject(this@SplashScreen)
        super.onCreate(savedInstanceState)
        binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel = ViewModelProvider(this@SplashScreen, mFactory).get(SplashViewModel::class.java)
        mViewModel.attachObservable.observe(this@SplashScreen, { startIntent(it) })
    }


    private fun startIntent(s: String) {
        Log.i("OLEG", "$s")
        when (s) {
            START_RESULT -> {
                startActivity(Intent(this@SplashScreen, ContainerFragment::class.java))
                finish()
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
                finish()
            }
        }
    }

    override fun getActivityComponent(): ActivityComponent =
        (application as MovieApplication).commonAppComponent.getActivityComponent(
            ActivityModule(
                this@SplashScreen
            )
        )
}