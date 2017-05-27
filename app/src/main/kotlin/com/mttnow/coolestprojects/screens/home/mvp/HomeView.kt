package com.mttnow.coolestprojects.screens.home.mvp

import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.widget.FrameLayout
import com.jakewharton.rxbinding.support.design.widget.itemSelections
import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.func.lazyView
import com.mttnow.coolestprojects.screens.fragments.HomeFragment
import com.mttnow.coolestprojects.screens.home.HomeActivity
import kotlinx.android.synthetic.main.activity_home.view.*

/**
 * Compound view for the homescreen
 */

private val FRAGMENT_TAG = "FRAGMNET_TAG"

class HomeView : FrameLayout {

  constructor(context: HomeActivity) : super(context) {
    init(context)
  }

  constructor(context: HomeActivity, attrs: AttributeSet) : super(context, attrs) {
    init(context, attrs)
  }

  constructor(context: HomeActivity, attrs: AttributeSet, defStyleAttr: Int) : super(context,
      attrs,
      defStyleAttr) {
    init(context, attrs)
  }

  constructor(context: HomeActivity, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
      context,
      attrs,
      defStyleAttr,
      defStyleRes) {
    init(context, attrs)
  }

  private var fragmentManager: FragmentManager? = null

  private fun init(activity: AppCompatActivity, attrs: AttributeSet? = null) {
    inflate(context, R.layout.activity_home, this)
    fragmentManager = activity.supportFragmentManager

    //Attach the home if not fragment is attached
    if (fragmentManager?.findFragmentByTag(FRAGMENT_TAG) == null) {
      swapFragment(HomeFragment())

    }
  }

  fun getBottomNav() = bottom_navigation

  fun swapFragment(fragment: Fragment?) {

    if (fragment == null) {
      return
    } else {
      fragmentManager?.let { fragmentManager ->
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
            .commit()
      }
    }
  }

  fun onBackPressed(): Boolean {


    //Back btn pressed with drawer closed and current fragment is not home, return to home
    if (fragmentManager?.findFragmentByTag(FRAGMENT_TAG) !is HomeFragment) {

      swapFragment(HomeFragment())
      return false
    }
    return true
  }

}
