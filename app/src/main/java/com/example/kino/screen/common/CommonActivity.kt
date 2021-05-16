package com.example.kino.screen.common

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.fragment.app.commit
import com.example.kino.R
import com.example.kino.TabFragment
import com.example.kino.databinding.CommonLayoutBinding
import com.example.kino.screen.allmovie.AllMovie
import com.example.kino.screen.common.ContainerId.GLOBAL_FRAME
import com.example.kino.screen.common.ScreenEnum.*
import com.example.kino.screen.containerfragment.CommonContainer
import com.example.kino.screen.detail.DetailFragment
import com.example.kino.screen.movie.MovieFragment
import com.example.kino.screen.splash.SplashFragment


class CommonActivity : AppCompatActivity(), CommonNavigation {

    private var _bindin: CommonLayoutBinding? = null
    private val binding get() = _bindin!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _bindin = CommonLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        openScreen(SPLASH, GLOBAL_FRAME)
    }

    override fun openScreen(screenEnum: ScreenEnum, containerId: ContainerId) {
        binding.root.tag = screenEnum
        when (screenEnum) {
            MOVIE -> transaction(screenEnum.transactionTag, MovieFragment(), containerId.id)
            SEARCH -> {
            }
            SERIAL -> {
            }
            SPLASH -> transaction(screenEnum.transactionTag, SplashFragment(), containerId.id)
            FAVORITE -> {
            }
            COMMONVIEW -> transaction(screenEnum.transactionTag, CommonContainer(), containerId.id)
            DETAIL -> transaction(screenEnum.transactionTag, DetailFragment(), containerId.id)
            ALL -> transaction(screenEnum.transactionTag, AllMovie(), containerId.id)
            TABS -> transaction(screenEnum.transactionTag, TabFragment(), containerId.id)
            NONE -> {
            }
            else -> {
            }
        }
    }

    private fun deleteOldFragment() {
        val oldFragment = supportFragmentManager.primaryNavigationFragment ?: return
        if (oldFragment::javaClass.name == CommonContainer()::javaClass.name) return
        supportFragmentManager.commit {
            remove(oldFragment)
        }
    }

    private fun transaction(TAG: String, fragmentCreate: Fragment, @IdRes containerId: Int) {
        deleteOldFragment()
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            addToBackStack(null)
            setTransition(TRANSIT_FRAGMENT_FADE)
            setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
            replace(containerId, fragmentCreate, TAG)
            setPrimaryNavigationFragment(fragmentCreate)
        }
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        val screenEnum = binding.root.tag as ScreenEnum
        when {
            screenEnum == TABS -> {
                supportFragmentManager.popBackStack()
                supportFragmentManager.popBackStack()
            }
            screenEnum == MOVIE -> {
                supportFragmentManager.popBackStackImmediate()
                finish()
            }
            count != 0 -> {
                supportFragmentManager.popBackStack()
            }
            else -> {
                super.onBackPressed()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.root.tag = null
        _bindin = null
    }
}