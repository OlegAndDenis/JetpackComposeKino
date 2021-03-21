package com.example.kino.screen.common

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.kino.R
import com.example.kino.applicationm.MovieApplication
import com.example.kino.comonnscreen.Base
import com.example.kino.databinding.SingleFragmentBinding
import com.example.kino.di.components.ActivityComponent
import com.example.kino.di.moduls.ActivityModule
import com.example.kino.screen.screncontainer.ContainerFragment
import com.example.kino.screen.screncontainer.ContainerViewModel
import com.example.kino.viewmodel.ViewModelFactory
import javax.inject.Inject

class SingleActivity : Base() {

    private lateinit var binding: SingleFragmentBinding

    @Inject
    lateinit var mFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getActivityComponent().inject(this@SingleActivity)
        binding = SingleFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        transitionOnOtherFragment("GlobalMenu", ContainerFragment(), supportFragmentManager)
    }

    override fun getActivityComponent(): ActivityComponent =
        (application as MovieApplication).commonAppComponent.getActivityComponent(
            ActivityModule(
                this@SingleActivity
            )
        )

    private fun transitionOnOtherFragment(
        TAG: String,
        newFragment: Fragment,
        manager: FragmentManager
    ) {
        val fragment: Fragment? = if (manager.findFragmentByTag(TAG) != null) {
            manager.findFragmentByTag(TAG)
        } else {
            newFragment
        }
        val mTranslationTree: FragmentTransaction = manager
            .beginTransaction()
            .addToBackStack(null)
            .setCustomAnimations(R.anim.to_left_in, R.anim.to_right_out)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        if (fragment != null) {
            mTranslationTree.replace(R.id.single_frame, fragment, TAG)
            mTranslationTree.commit()
        }
    }

    override fun onRestart() {
        super.onRestart()
    }
}