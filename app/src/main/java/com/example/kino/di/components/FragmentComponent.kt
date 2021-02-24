package com.example.kino.di.components

import com.example.kino.screen.moviefragment.MovieFragment
import com.example.kino.di.scope.FragmentScope
import dagger.Subcomponent

@FragmentScope
@Subcomponent()
interface FragmentComponent {
    fun inject(movieFragment: MovieFragment)
}