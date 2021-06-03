package com.example.kino.screen.pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kino.screen.pager.overview.OverviewFragment

class FragmentPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    var map: Map<Fragment, String> = emptyMap()

    override fun getItemCount(): Int = map.size

    override fun createFragment(position: Int): Fragment {
      val fragment = map.keys.elementAt(position)
        return if (fragment is OverviewFragment) {
            fragment
        } else {
            Fragment()
        }
    }

    override fun getItemViewType(position: Int): Int = super.getItemViewType(position)
}