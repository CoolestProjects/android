package com.mttnow.coolestprojects.screens.fragments.dagger

import com.mttnow.coolestprojects.app.dagger.AppComponent
import com.mttnow.coolestprojects.screens.fragments.*
import dagger.Component
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FragmentScope

@FragmentScope
@Component(dependencies = arrayOf(AppComponent::class))
interface BaseFragmentComponent {
    fun inject(baseFragment: BaseFragment)
}