package com.example.kino.comonnscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kino.di.components.FragmentComponent

abstract class BaseFragment : Fragment(){

    abstract override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?

    abstract override fun onViewCreated(view: View, savedInstanceState: Bundle?)

    abstract fun getFragmentComponent() : FragmentComponent
}