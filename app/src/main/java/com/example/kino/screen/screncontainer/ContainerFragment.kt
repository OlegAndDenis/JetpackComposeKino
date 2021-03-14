package com.example.kino.screen.screncontainer

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import com.example.kino.comonnscreen.Base
import com.example.kino.R
import com.example.kino.applicationm.MovieApplication
import com.example.kino.databinding.ActivityMainBinding
import com.example.kino.di.components.ActivityComponent
import com.example.kino.di.moduls.ActivityModule
import com.example.kino.viewmodel.ViewModelFactory
import javax.inject.Inject

class ContainerFragment : Base() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: ContainerViewModel

    @Inject
    lateinit var mFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent().inject(this@ContainerFragment)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        mViewModel =
            ViewModelProvider(this@ContainerFragment, mFactory).get(ContainerViewModel::class.java)
        binding.bottomNavigation.setOnNavigationItemSelectedListener { pressTheButtonNavigator(it) }
        binding.bottomNavigation.selectedItemId = R.id.butt_film
    }

    private fun pressTheButtonNavigator(item: MenuItem): Boolean {
        mViewModel.pressTheButtonNavigator(item.itemId, supportFragmentManager)
        if (item.itemId != R.id.butt_search) {
            supportActionBar!!.title = item.title
            binding.toolbar.tag = "no"
        } else {
            binding.toolbar.tag = "search"
        }
        invalidateOptionsMenu()
        return true
    }

    override fun getActivityComponent(): ActivityComponent =
        (application as MovieApplication).commonAppComponent.getActivityComponent(
            ActivityModule(
                this@ContainerFragment
            )
        )

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.toolbar, it)
            val searchMenu = it.findItem(R.id.menu_search)
            val search: SearchView = searchMenu.actionView as SearchView
            search.isIconifiedByDefault = false
            search.queryHint = "search: movie, serial"
            search.isSubmitButtonEnabled = true
            search.gravity = Gravity.CENTER
            searchMenu.isVisible = binding.toolbar.tag == "search"
        }
        return true
    }

    override fun onRestart() {
        super.onRestart()

    }
}