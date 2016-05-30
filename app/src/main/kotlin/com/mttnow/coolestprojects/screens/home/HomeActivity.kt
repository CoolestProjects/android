package com.mttnow.coolestprojects.screens.home

import android.os.Bundle
import com.mttnow.coolestprojects.app.CoolestProjectsApp
import com.mttnow.coolestprojects.screens.common.LifecyclePresenter
import com.mttnow.coolestprojects.screens.common.PresenterActivity
import com.mttnow.coolestprojects.screens.home.dagger.DaggerHomeComponent
import com.mttnow.coolestprojects.screens.home.dagger.HomeModule
import com.mttnow.coolestprojects.screens.home.mvp.HomePresenter
import com.mttnow.coolestprojects.screens.home.mvp.HomeView
import javax.inject.Inject

class HomeActivity : PresenterActivity() {

  @Inject
  lateinit var homeView : HomeView

  @Inject
  lateinit var hompresenter: HomePresenter

  override fun onCreatePresenter(savedInstanceState: Bundle?): LifecyclePresenter? {
    super.onCreatePresenter(savedInstanceState)

    DaggerHomeComponent.builder()
    .homeModule(HomeModule(this))
    .appComponent(CoolestProjectsApp.get(this).appComponent)
    .build().intject(this)

    setContentView(homeView)
    return hompresenter
  }
}
