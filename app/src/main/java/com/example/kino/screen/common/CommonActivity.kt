package com.example.kino.screen.common

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kino.R
import com.example.kino.databinding.CommonLayoutBinding
import com.example.kino.screen.containerfragment.CommonContainer
import com.example.kino.screen.splash.SplashFragment

class CommonActivity : AppCompatActivity(), CommonNavigation {

    private var _bindin: CommonLayoutBinding? = null
    private val binding get() = _bindin!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bindin = CommonLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openScreen(ScreenEnum.SPLASH)
    }

    override fun openScreen(screenEnum: ScreenEnum) {
        when (screenEnum) {
            ScreenEnum.MOVIE -> {
            }
            ScreenEnum.SEARCH -> {
            }
            ScreenEnum.SERIAL -> {
            }
            ScreenEnum.SPLASH -> transaction(screenEnum.transactionTag, SplashFragment())
            ScreenEnum.NONE -> {
            }
            ScreenEnum.FAVORITE -> {
            }
            ScreenEnum.COMMONVIEW -> transaction(screenEnum.transactionTag, CommonContainer())
            else -> {
            }
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
            mTranslationTree.replace(binding.commonFrame.id, fragment, TAG)
            mTranslationTree.commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindin = null
    }
}