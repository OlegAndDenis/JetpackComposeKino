package com.example.kino.di.components

import com.example.kino.screen.splash.SplashScreen
import com.example.kino.di.moduls.ActivityModule
import com.example.kino.di.scope.ActivityScope
import com.example.kino.screen.screncontainer.ContainerFragment
import com.example.kino.screen.search.SearchScreen
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {

    fun getFragmentComponent(): FragmentComponent

    fun inject(container: ContainerFragment)

    fun inject(screen: SplashScreen)
}