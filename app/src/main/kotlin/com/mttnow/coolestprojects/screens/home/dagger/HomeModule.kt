package com.mttnow.coolestprojects.screens.home.dagger

import com.mttnow.coolestprojects.network.CoolestProjectsService
import com.mttnow.coolestprojects.rx.RxSchedulers
import com.mttnow.coolestprojects.screens.home.HomeActivity
import com.mttnow.coolestprojects.screens.home.mvp.HomeInteractor
import com.mttnow.coolestprojects.screens.home.mvp.HomePresenter
import com.mttnow.coolestprojects.screens.home.mvp.HomeView
import dagger.Module
import dagger.Provides

@Module
class HomeModule(private val context: HomeActivity) {

  @HomeScope
  @Provides
  fun homeView() = HomeView(context)

  @HomeScope
  @Provides
  fun homePresenter(homeView: HomeView, homeInteractor: HomeInteractor, rxSchedulers: RxSchedulers)
      = HomePresenter(homeView, rxSchedulers, homeInteractor)

  @HomeScope
  @Provides
  fun homeInteractor(coolestProjectsService: CoolestProjectsService)
      = HomeInteractor(context, coolestProjectsService)

}