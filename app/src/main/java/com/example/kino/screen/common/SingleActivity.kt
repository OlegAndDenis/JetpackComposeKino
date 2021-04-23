package com.example.kino.screen.common

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kino.CommonFactory
import com.example.kino.R
import com.example.kino.comonnscreen.Base
import com.example.kino.databinding.SingleFragmentBinding
import com.example.kino.screen.screncontainer.ContainerFragment
import com.example.kino.screen.splash.SplashScreen
import timber.log.Timber

class SingleActivity : Base() {

    private lateinit var binding: SingleFragmentBinding

    private val viewModel: ViewModelTransaction by viewModels { CommonFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SingleFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.attachObservable.observe(this@SingleActivity, this::transitionOnOtherFragment)
        viewModel.transaction("splashScreen")
        Timber.i("++++")
    }


    private fun transitionOnOtherFragment(
        TAG: String) {
        removeScreen()
        val fragmentCreate = when(TAG) {
            "globalMenu" -> ContainerFragment()
            "splashScreen" -> SplashScreen()
            else -> SplashScreen()
        }
        Timber.i(TAG)
        transaction(TAG, fragmentCreate)
    }

    private fun removeScreen() {
        val oldScreen = supportFragmentManager.findFragmentByTag("splashScreen")
        if (oldScreen != null) {
            supportFragmentManager.beginTransaction().remove(oldScreen).commit()
        }
    }

    private fun transaction(TAG: String, fragmentCreate: Fragment) {
        val fragment: Fragment? = if (supportFragmentManager.findFragmentByTag(TAG) != null) {
            supportFragmentManager.findFragmentByTag(TAG)
        } else {
            fragmentCreate
        }
        val mTranslationTree: FragmentTransaction = supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.to_left_in, R.anim.to_right_out)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (fragment != null) {
            mTranslationTree.replace(R.id.single_frame, fragment, TAG)
            mTranslationTree.commit()
        }
    }

    override fun onRestart() {
        super.onRestart()
    }
}