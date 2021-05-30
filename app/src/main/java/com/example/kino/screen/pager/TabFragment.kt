package com.example.kino.screen.pager

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.kino.CommonFactory
import com.example.kino.databinding.TabHostLayoutBinding
import com.example.kino.launchView
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.CommonNavigation
import com.example.kino.screen.common.TransactionViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.onEach

class TabFragment : BaseFragment(), TabLayoutMediator.TabConfigurationStrategy {

    private var _binding: TabHostLayoutBinding? = null
    private val binding get() = _binding!!

    private val viewModelTransaction: TransactionViewModel by activityViewModels { CommonFactory }

    private lateinit var adapter: FragmentPager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = TabHostLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = FragmentPager(childFragmentManager, lifecycle)
        binding.viewPager.adapter = adapter
        binding.viewPager.overScrollMode = View.OVER_SCROLL_NEVER

        viewModelTransaction.mapFragment.onEach(this::setAdapter).launchView(viewLifecycleOwner)
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
        binding.root.removeAllViews()
        _binding = null
    }
}