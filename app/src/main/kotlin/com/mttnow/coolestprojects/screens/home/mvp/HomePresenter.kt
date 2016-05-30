package com.mttnow.coolestprojects.screens.home.mvp

import com.mttnow.coolestprojects.rx.RxSchedulers
import com.mttnow.coolestprojects.screens.common.LifecyclePresenter

class HomePresenter(private val homeView: HomeView,
                    private val rxSchedulers: RxSchedulers,
                    private val homeInteractor: HomeInteractor) : LifecyclePresenter {

  override fun onCreate() {

    //do view and rx stuff
  }
}