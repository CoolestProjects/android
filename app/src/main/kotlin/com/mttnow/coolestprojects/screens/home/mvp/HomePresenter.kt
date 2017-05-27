package com.mttnow.coolestprojects.screens.home.mvp

import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import android.widget.TextView
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

    setContentView(R.layout.activity_home)

    val fragmentManager = getSupportFragmentManager()
    fragment = HomeFragment()
    val transaction = fragmentManager.beginTransaction()
    transaction.add(R.id.fragment_container, fragment).commit()

    val textHome = findViewById(R.id.action_home) as TextView
    val textMaps = findViewById(R.id.action_maps) as TextView
    val textHalls = findViewById(R.id.action_halls) as TextView
    val textAbout = findViewById(R.id.action_about) as TextView

    val bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView

    bottomNavigationView.setOnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.action_home -> {
          textHome.setVisibility(View.VISIBLE)
          fragment = HomeFragment()
          textMaps.setVisibility(View.GONE)
          textHalls.setVisibility(View.GONE)
          textAbout.setVisibility(View.GONE)
        }
        R.id.action_maps -> {
          textHome.setVisibility(View.GONE)
          textMaps.setVisibility(View.VISIBLE)
          fragment = MapsFragment()
          textHalls.setVisibility(View.GONE)
          textAbout.setVisibility(View.GONE)
        }
        R.id.action_halls -> {
          textHome.setVisibility(View.GONE)
          textMaps.setVisibility(View.GONE)
          textHalls.setVisibility(View.VISIBLE)
          fragment = StagesFragment()
          textAbout.setVisibility(View.GONE)
        }
        R.id.action_about ->
          //  textHome.setVisibility(View.GONE);
          // textMaps.setVisibility(View.GONE);
          //textHalls.setVisibility(View.GONE);
          // textAbout.setVisibility(View.VISIBLE);
          fragment = AboutFragment()
      }
      val transaction = fragmentManager.beginTransaction()
      transaction.replace(R.id.fragment_container, fragment)
      transaction.addToBackStack(null)
      transaction.commit()
      true
    }


  }

  fun observeBottomNavSelection() {
//
    val selectedItem = homeView.getBottomNavMenuSelection()
    print("Selected item" + selectedItem)
//    val fragment = HomeFragment()
//      if(selectedItem == R.id.action_maps) {
//        fragment = MapsFragment()
//      } else if (selectedItem)
//
//      when (selectedItem) {
//        R.id.action_maps -> MapsFragment()
//        R.id.action_halls -> StagesFragment()
//        R.id.action_about -> AboutFragment()
//        else -> null
}


    //homeView.swapFragment(fragment)

  //}

//
//  fun observeMenuClicks() {
//    //TODO: Remove if dropping side menu
//    homeView.getNavMenuClicks()
//        .map { it.itemId }
//        .map {
//          when (it) {
//            R.id.nav_speakers -> SpeakersFragment()
//            R.id.nav_maps -> MapsFragment()
//            R.id.nav_stages -> StagesFragment()
//            R.id.nav_home -> HomeFragment()
//            R.id.nav_stages -> StagesFragment()
//            R.id.nav_sponsors -> SponsorsFragment()
//            R.id.nav_projects -> ProjectsFragment()
//            R.id.nav_about -> AboutFragment()
//            else -> null
//          }
//        }
//        .filter { it != null }
//        .subscribe({ homeView.swapFragment(it) })
//
//  }
}