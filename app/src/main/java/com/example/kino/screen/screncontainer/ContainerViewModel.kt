package com.example.kino.screen.screncontainer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kino.R
import com.example.kino.network.NetworkRepository
import com.example.kino.network.model.common.NetworkItem
import com.example.kino.screen.moviefragment.MovieFragment
import com.example.kino.screen.search.SearchFragment
import com.example.kino.screen.serialfragment.SerialFragment
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class ContainerViewModel(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val TAG_MOVIE = "movie"
    private val TAG_SEARCH = "search"
    private val TAG_FAVORITE = "favorite"
    private val TAG_SERIAL = "serial"

    private val _search: MutableLiveData<Map<NetworkItem, List<NetworkItem>>> = MutableLiveData()
    val search: LiveData<Map<NetworkItem, List<NetworkItem>>> = _search

    fun pressTheButtonNavigator(id: Int, manager: FragmentManager) {
        when (id) {
            R.id.butt_film -> {
                transitionOnOtherFragment(
                    TAG_MOVIE,
                    MovieFragment(),
                    manager
                )
            }
            R.id.butt_search -> {
                transitionOnOtherFragment(
                    TAG_SEARCH,
                    SearchFragment(),
                    manager
                )
            }
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

    fun startSearch(query: String?, page: Int) {
        query?.let {
            networkRepository.getSearch(it, page)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe({result -> _search.postValue(result.result)},
                    Timber::e
                )
        }
    }

    private fun revertList(list: List<NetworkItem>) {
    }

    fun clearResult() {
    }
}