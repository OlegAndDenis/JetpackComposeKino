package com.example.kino.screen.containerfragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.example.kino.R
import com.example.kino.databinding.ContainerLayoutBinding
import com.example.kino.launchView
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.ContainerId.*
import com.example.kino.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.flow.onEach

class NavigationFragment : BaseFragment(), SearchView.OnQueryTextListener {

    private var _binding: ContainerLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ContainerLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(binding.containerToolbar)

        val navGraphIds = listOf(
            R.navigation.movie_navigation,
//            R.navigation.searials_navigation,
//            R.navigation.search_navigation,
//            R.navigation.favorite_navigation
        )

        binding.containerBottomNavigation.setupWithNavController(
            navGraphIds,
            childFragmentManager,
            binding.containerFrame.id,
            requireActivity().intent
        ).onEach(this::onNavigationTitleSelected).launchView(viewLifecycleOwner)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.let {
            val searchMenu = it.findItem(R.id.menu_search)
            val search: SearchView = searchMenu.actionView as SearchView
            search.isIconifiedByDefault = false
            search.queryHint = "search: movie, serial"
            search.isSubmitButtonEnabled = true
            search.gravity = Gravity.CENTER
            search.setOnQueryTextListener(this@NavigationFragment)
            searchMenu.isVisible = binding.containerToolbar.tag == R.id.butt_search
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
        super.onPrepareOptionsMenu(menu)
    }

    private fun onNavigationTitleSelected(item: MenuItem?) {
        item?.let {
            binding.containerToolbar.title = item.title
            if (item.itemId == R.id.butt_search) {
                binding.containerToolbar.title = ""
            }
            binding.containerToolbar.tag = item.itemId
            (activity as AppCompatActivity).invalidateOptionsMenu()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).setSupportActionBar(null)
        binding.containerToolbar.tag = null
        binding.containerBottomNavigation.setOnNavigationItemSelectedListener(null)
        binding.root.removeAllViews()
        onDestroyOptionsMenu()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }
}