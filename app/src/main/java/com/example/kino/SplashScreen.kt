package com.example.kino

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.work.WorkInfo
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
    private val WORK_RESULT = "work_result"

    @Inject
    lateinit var mFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent().inject(this@SplashScreen)
        val binding = SplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    init {
        mViewModel = ViewModelProvider(this@SplashScreen, mFactory).get(SplashViewModel::class.java)
    }

    private fun startIntent() {

    }

    override fun getActivityComponent(): ActivityComponent  =
        (application as MovieApplication).commonAppComponent.getActivityComponent(ActivityModule(this@SplashScreen))
}