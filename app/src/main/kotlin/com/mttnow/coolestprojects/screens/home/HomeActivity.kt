package com.mttnow.coolestprojects.screens.home

import android.app.Fragment
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.app.CoolestProjectsApp
import com.mttnow.coolestprojects.screens.common.LifecyclePresenter
import com.mttnow.coolestprojects.screens.common.PresenterActivity
import com.mttnow.coolestprojects.screens.fragments.MapsFragment
import com.mttnow.coolestprojects.screens.fragments.SpeakersFragment
import com.mttnow.coolestprojects.screens.fragments.StagesFragment
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


    setupNavigationView();
    setSupportActionBar(homeView.toolbar)


    supportActionBar?.setDisplayShowTitleEnabled(false)
    val toggle = ActionBarDrawerToggle(this, homeView.drawerLayout, homeView.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
    homeView.drawerLayout.addDrawerListener(toggle)
    toggle.syncState()

    setContentView(homeView)
    return hompresenter
  }

  private fun setupNavigationView() {
    homeView.navView.setNavigationItemSelectedListener {
      when (it.itemId) {
        R.id.nav_home -> {
          while (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStackImmediate()
          }

          homeView.icon.visibility = View.VISIBLE
          homeView.title.visibility = View.GONE
        }
        R.id.nav_speakers -> replace(SpeakersFragment(), getString(R.string.title_speakers))
        R.id.nav_maps -> replace(MapsFragment(), getString(R.string.title_maps))
        R.id.nav_stages -> replace(StagesFragment(), getString(R.string.title_stages))
        else -> null
      }
      homeView.drawerLayout.closeDrawers()

      true
    }

  }

  private fun replace(fragment: Fragment, title: String) {

    homeView.icon.visibility = View.GONE
    homeView.title.visibility = View.VISIBLE
    homeView.title.text = title

    if(fragmentManager.backStackEntryCount > 0) {
      fragmentManager.popBackStackImmediate()
    }
    fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, title)
            .addToBackStack(null)
            .commit()
  }

  override fun onBackPressed() {
    if(homeView.drawerLayout.isDrawerOpen(GravityCompat.START)) {
      homeView.drawerLayout.closeDrawer(GravityCompat.START)
      return
    }
    super.onBackPressed()

    //Back btn pressed with drawer closed, then I go back to the home (So I show the logo on the Toolbar)
    if (fragmentManager.backStackEntryCount == 0) {
      homeView.icon.visibility = View.VISIBLE
      homeView.title.visibility = View.GONE
      homeView.navView.menu.findItem(R.id.nav_home).isChecked = true
    }
  }
}
