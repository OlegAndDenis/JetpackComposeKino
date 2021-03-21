package com.example.kino.di.components

import com.example.kino.screen.moviefragment.MovieFragment
import com.example.kino.di.scope.FragmentScope
import com.example.kino.screen.screncontainer.ContainerFragment
import com.example.kino.screen.search.SearchScreen
import com.example.kino.screen.serialfragment.SerialFragment
import com.example.kino.screen.serialfragment.SerialViewModel
import dagger.Subcomponent

@FragmentScope
@Subcomponent()
interface FragmentComponent {
    fun inject(movieFragment: MovieFragment)
    fun inject(serialFragment: SerialFragment)
    fun inject(searchScreen: SearchScreen)
    fun inject(containerFragment: ContainerFragment)
}