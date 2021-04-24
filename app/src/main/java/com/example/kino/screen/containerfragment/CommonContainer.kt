package com.example.kino.screen.containerfragment

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.kino.R
import com.example.kino.databinding.ContainerLayoutBinding
import com.example.kino.screen.common.BaseFragment
import com.example.kino.screen.common.CommonNavigation
import com.example.kino.screen.common.ContainerId.*
import com.example.kino.screen.common.ScreenEnum
import com.google.android.material.bottomnavigation.BottomNavigationView
import timber.log.Timber

class CommonContainer : BaseFragment(), BottomNavigationView.OnNavigationItemSelectedListener,
    SearchView.OnQueryTextListener {

    private var _binding: ContainerLayoutBinding? = null
    private val binding get() = _binding!!
    private lateinit var navigation: CommonNavigation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = ContainerLayoutBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        activity?.let { navigation = it as CommonNavigation }
        binding.containerBottomNavigation.setOnNavigationItemSelectedListener(this)
        binding.containerBottomNavigation.selectedItemId = R.id.butt_film
        (activity as AppCompatActivity).setSupportActionBar(binding.containerToolbar)
        Timber.i("Common")
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
            search.setOnQueryTextListener(this@CommonContainer)
            searchMenu.isVisible = binding.containerToolbar.tag == R.id.butt_search
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
        super.onPrepareOptionsMenu(menu)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navigation.openScreen(ScreenEnum.findById(item.itemId), BOTTOM_NAVIGATION_FRAME)
        binding.containerToolbar.title = item.title
        if (item.itemId == R.id.butt_search) {
            binding.containerToolbar.title = ""
        }
        binding.containerToolbar.tag = item.itemId
        (activity as AppCompatActivity).invalidateOptionsMenu()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }
}