package com.example.kino.screen.pager

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kino.NavigationUi.*
import com.example.kino.databinding.TabHostLayoutBinding
import com.example.kino.screen.common.BaseFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import timber.log.Timber

class TabFragment : BaseFragment(), TabLayoutMediator.TabConfigurationStrategy {

    private var _binding: TabHostLayoutBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: FragmentPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = TabHostLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FragmentPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        binding.viewPager.adapter = adapter
        binding.viewPager.overScrollMode = View.OVER_SCROLL_NEVER
        getData()
    }

    private fun getData() {
        val argument = arguments ?: Bundle.EMPTY
        if (argument.isEmpty) return // Fixme Обработать действие назад
        if (argument.containsKey(LIST_FRAGMENT.name)) {
            val map: Map<Fragment, String> = (argument.getSerializable(LIST_FRAGMENT.name)
                ?: emptyMap<Fragment, String>()) as Map<Fragment, String>
            setAdapter(map)
        }
    }

    private fun setAdapter(map: Map<Fragment, String>) {
        adapter.map = map
        adapter.notifyDataSetChanged()
        val tabMediator = TabLayoutMediator(
            binding.tabs,
            binding.viewPager,
            this
        )
        tabMediator.attach()
    }

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {
        tab.text = position.toString()
        adapter.map
        tab.view.gravity = Gravity.CENTER
        tab.text = adapter.map?.values?.elementAt(position)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.viewPager.adapter = null
        binding.root.removeAllViews()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy")
    }
}