package com.mttnow.coolestprojects.screens.home.dagger

import com.mttnow.coolestprojects.app.dagger.AppComponent
import com.mttnow.coolestprojects.screens.home.HomeActivity
import dagger.Component
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class HomeScope

@HomeScope
@Component(modules = arrayOf(HomeModule::class), dependencies = arrayOf(AppComponent::class))
interface HomeComponent {

  fun intject(homeActivity: HomeActivity)

}