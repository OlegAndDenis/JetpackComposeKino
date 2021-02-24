package com.example.kino.screncontainer

import android.os.Bundle
import android.view.MenuItem
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

    private lateinit var binding : ActivityMainBinding
    private lateinit var mViewModel: ContainerViewModel

    @Inject
    lateinit var mFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent().inject(this@ContainerFragment)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        mViewModel = ViewModelProvider(this@ContainerFragment, mFactory).get(ContainerViewModel::class.java)
        binding.bottomNavigation.setOnNavigationItemSelectedListener { pressTheButtonNavigator(it) }
        binding.bottomNavigation.selectedItemId = R.id.butt_film
    }

    private fun pressTheButtonNavigator(item: MenuItem): Boolean {
        mViewModel.pressTheButtonNavigator(item.itemId, supportFragmentManager)
        supportActionBar!!.title = item.title
        return true
    }

     override fun getActivityComponent(): ActivityComponent =
        (application as MovieApplication).commonAppComponent.getActivityComponent(ActivityModule(this@ContainerFragment))
}