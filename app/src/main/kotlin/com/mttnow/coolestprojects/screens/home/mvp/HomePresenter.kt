package com.mttnow.coolestprojects.screens.home.mvp

import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.rx.RxSchedulers
import com.mttnow.coolestprojects.screens.common.LifecyclePresenter
import com.mttnow.coolestprojects.screens.fragments.*

class HomePresenter(private val homeView: HomeView,
                    private val rxSchedulers: RxSchedulers,
                    private val homeInteractor: HomeInteractor) : LifecyclePresenter {

  override fun onCreate() {

    //do view and rx stuff
   // observeMenuClicks()
  }

  fun observeMenuClicks() {

    when (homeView.getSelectedItem()) {
      R.id.action_home -> homeView.swapFragment(HomeFragment())
      R.id.action_halls ->  homeView.swapFragment(StagesFragment())
      R.id.action_maps ->  homeView.swapFragment(MapsFragment())
      R.id.action_about ->  homeView.swapFragment(AboutFragment())
      else -> null
    }
  //      }
//        .filter { selectedItem != null }
//        .subscribe({ homeView.swapFragment(selectedItem) })
  }

}
