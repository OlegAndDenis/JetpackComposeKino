package com.example.kino.screncontainer

import android.app.Application
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.AndroidViewModel
import com.example.kino.R
import com.example.kino.screen.moviefragment.MovieFragment
import com.example.kino.screen.serialfragment.SerialFragment

class ContainerViewModel(application: Application) : AndroidViewModel(application) {

    private val TAG_MOVIE = "movie"
    private val TAG_SEARCH = "search"
    private val TAG_FAVORITE = "favorite"
    private val TAG_SERIAL = "serial"

    fun pressTheButtonNavigator(id: Int, manager: FragmentManager) {
        when (id) {
            R.id.butt_film -> {
                transitionOnOtherFragment(
                    TAG_MOVIE,
                    MovieFragment(),
                    manager
                )
            }
//            R.id.butt_search -> {
//                transitionOnOtherFragment(
//                    TAG_SEARCH,
//                    MapsFragment(),
//                    manager
//                )
//            }
//            R.id.butt_favorite -> {
//                transitionOnOtherFragment(
//                    TAG_FAVORITE,
//                    FavoriteFragment(),
//                    manager
//                )
//                mPressPos.setValue(3)
//            }
            R.id.butt_serial -> {
                transitionOnOtherFragment(
                    TAG_SERIAL,
                    SerialFragment(),
                    manager
                )
            }
        }
    }

    private fun transitionOnOtherFragment(
        TAG: String,
        newFragment: Fragment,
        manager: FragmentManager
    ) {
        val fragment: Fragment? = if (manager.findFragmentByTag(TAG) != null) {
            manager.findFragmentByTag(TAG)
        } else {
            newFragment
        }
        val mTranslationTree: FragmentTransaction = manager
            .beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.to_left_in, R.anim.to_right_out)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (fragment != null) {
            mTranslationTree.replace(R.id.frame_layout, fragment, TAG)
            mTranslationTree.commit()
        }
    }
}