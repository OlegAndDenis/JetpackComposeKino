package com.example.kino.screen.containerfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.kino.R
import com.example.kino.databinding.ContainerLayoutBinding
import com.example.kino.screen.common.BaseFragment
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import timber.log.Timber

class CommonContainer : BaseFragment(), BottomNavigationView.OnNavigationItemSelectedListener {

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
        binding.containerBottomNavigation.setOnNavigationItemSelectedListener(this)
        binding.containerBottomNavigation.selectedItemId = R.id.butt_film
        (activity as AppCompatActivity).setSupportActionBar(binding.containerToolbar)
        Timber.i("Common")
        setHasOptionsMenu(true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.containerToolbar.title = item.title
        (activity as AppCompatActivity).invalidateOptionsMenu()
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}