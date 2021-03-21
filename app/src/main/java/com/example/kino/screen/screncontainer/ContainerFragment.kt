package com.example.kino.screen.screncontainer

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.kino.R
import com.example.kino.comonnscreen.BaseFragment
import com.example.kino.databinding.ActivityMainBinding
import com.example.kino.di.components.FragmentComponent
import com.example.kino.screen.common.SingleActivity
import com.example.kino.viewmodel.ViewModelFactory
import javax.inject.Inject

class ContainerFragment : BaseFragment(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: ContainerViewModel

    @Inject
    lateinit var mFactory: ViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        getFragmentComponent().inject(this@ContainerFragment)
        binding = ActivityMainBinding.inflate(layoutInflater)
        (activity as SingleActivity).setSupportActionBar(binding.toolbar)
        mViewModel =
            ViewModelProvider(activity!!, mFactory).get(ContainerViewModel::class.java)
        binding.bottomNavigation.setOnNavigationItemSelectedListener { pressTheButtonNavigator(it) }
        binding.bottomNavigation.selectedItemId = R.id.butt_film
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
    }

    override fun getFragmentComponent(): FragmentComponent =
        (activity as SingleActivity).getActivityComponent().getFragmentComponent()


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
       return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        mViewModel.startSearch(newText)
        return false
    }
}