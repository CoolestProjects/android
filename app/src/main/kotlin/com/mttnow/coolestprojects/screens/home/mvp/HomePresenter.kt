package com.mttnow.coolestprojects.screens.home.mvp

import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.rx.RxSchedulers
import com.mttnow.coolestprojects.screens.common.LifecyclePresenter
import com.mttnow.coolestprojects.screens.fragments.HomeFragment
import com.mttnow.coolestprojects.screens.fragments.MapsFragment
import com.mttnow.coolestprojects.screens.fragments.SpeakersFragment
import com.mttnow.coolestprojects.screens.fragments.StagesFragment

class HomePresenter(private val homeView: HomeView,
                    private val rxSchedulers: RxSchedulers,
                    private val homeInteractor: HomeInteractor) : LifecyclePresenter {

  override fun onCreate() {

    //do view and rx stuff
    observeMenuClicks()
  }

  fun observeMenuClicks() {

    homeView.getNavMenuClicks()
        .map { it.itemId }
        .map {
          when (it) {
            R.id.nav_speakers -> SpeakersFragment.newInstance()
            R.id.nav_maps -> MapsFragment.newInstance()
            R.id.nav_stages -> StagesFragment.newInstance()
            R.id.nav_home -> HomeFragment.newInstance()
            else -> null
          }
        }
        .filter { it != null }
        .subscribe({ homeView.swapFragment(it) })
  }
}