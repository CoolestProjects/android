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
    observeMenuClicks()
  }

  fun observeMenuClicks() {

    homeView.getNavMenuClicks()
        .map { it.itemId }
        .map {
          when (it) {
            R.id.nav_speakers -> SpeakersFragment()
            R.id.nav_maps -> MapsFragment()
            R.id.nav_stages -> StagesFragment()
            R.id.nav_home -> HomeFragment()
            R.id.nav_stages -> StagesFragment()
            R.id.nav_sponsors -> SponsorsFragment()
            R.id.nav_about -> AboutFragment()
            else -> null
          }
        }
        .filter { it != null }
        .subscribe({ homeView.swapFragment(it) })
  }
}