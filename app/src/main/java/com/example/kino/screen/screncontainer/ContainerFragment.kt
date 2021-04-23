package com.example.kino.screen.screncontainer

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.viewModels
import com.example.kino.CommonFactory
import com.example.kino.R
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.ActivityMainBinding
import com.example.kino.screen.common.SingleActivity
import timber.log.Timber

class ContainerFragment : BaseFragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private val mViewModel: ContainerViewModel by viewModels { CommonFactory }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.i("++++")
        (activity as SingleActivity).setSupportActionBar(binding.toolbar)
        binding.bottomNavigation.setOnNavigationItemSelectedListener { pressTheButtonNavigator(it) }
        binding.bottomNavigation.selectedItemId = R.id.butt_film
        setHasOptionsMenu(true)
    }


    private fun pressTheButtonNavigator(item: MenuItem): Boolean {
        mViewModel.pressTheButtonNavigator(item.itemId, childFragmentManager)
        if (item.itemId != R.id.butt_search) {
            (activity as SingleActivity).supportActionBar!!.title = item.title
            binding.toolbar.tag = "no"
        } else {
            binding.toolbar.tag = "search"
        }
        (activity as SingleActivity).invalidateOptionsMenu()
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.let {
            val searchMenu = it.findItem(R.id.menu_search)
            val search: SearchView = searchMenu.actionView as SearchView
            search.isIconifiedByDefault = false
            search.queryHint = "search: movie, serial"
            search.isSubmitButtonEnabled = true
            search.gravity = Gravity.CENTER
            search.setOnQueryTextListener(this@ContainerFragment)
            searchMenu.isVisible = binding.toolbar.tag == "search"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.toolbar, menu)
        super.onPrepareOptionsMenu(menu)
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        mViewModel.clearResult()
        if (query!!.length >= 3) {
            mViewModel.startSearch(query, 1)
        }
       return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        mViewModel.clearResult()
        if (newText!!.length > 2) {
            mViewModel.startSearch(newText, 1)
        }
        return false
    }
}