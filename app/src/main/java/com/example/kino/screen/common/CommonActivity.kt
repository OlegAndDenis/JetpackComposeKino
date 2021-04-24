package com.example.kino.screen.common

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.kino.R
import com.example.kino.databinding.CommonLayoutBinding
import com.example.kino.network.model.movie.Movie
import com.example.kino.screen.common.ContainerId.*
import com.example.kino.screen.common.ScreenEnum.*
import com.example.kino.screen.containerfragment.CommonContainer
import com.example.kino.screen.movie.MovieFragment
import com.example.kino.screen.splash.SplashFragment

class CommonActivity : AppCompatActivity(), CommonNavigation {

    private var _bindin: CommonLayoutBinding? = null
    private val binding get() = _bindin!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bindin = CommonLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openScreen(COMMONVIEW, GLOBAL_FRAME)
    }

    override fun openScreen(screenEnum: ScreenEnum, containerId: ContainerId) {
        when (screenEnum) {
            MOVIE -> transaction(screenEnum.transactionTag, MovieFragment(), containerId.id)
            SEARCH -> { }
            SERIAL -> { }
            SPLASH -> transaction(screenEnum.transactionTag, SplashFragment(), containerId.id)
            FAVORITE -> { }
            COMMONVIEW -> transaction(screenEnum.transactionTag, CommonContainer(), containerId.id)
            NONE -> { }
            else -> { }
        }
    }

    private fun transaction(TAG: String, fragmentCreate: Fragment, @IdRes containerId: Int) {
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
            mTranslationTree.replace(containerId, fragment, TAG)
            mTranslationTree.commit()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        _bindin = null
    }
}