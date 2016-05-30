package com.mttnow.coolestprojects.screens.home.mvp

import android.content.Context
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.widget.Toolbar
import android.util.AttributeSet
import android.widget.FrameLayout
import com.jakewharton.rxbinding.view.clicks
import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.func.lazyView

/**
 * Compound view for the homescreen
 */
class HomeView : FrameLayout {

  constructor(context: Context) : super(context) {
    init()
  }

  constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
    init(attrs)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init(attrs)
  }

  constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
    init(attrs)
  }

  val drawerLayout: DrawerLayout by lazyView { findViewById(R.id.home_drawer_layout) }
  val navView: NavigationView by lazyView { findViewById(R.id.left_nav_drawer) }
  val toolbar: Toolbar by lazyView { findViewById(R.id.home_toolbar) }

  private fun init(attrs: AttributeSet? = null) {
    inflate(context, R.layout.activity_home, this)
  }

  override fun onFinishInflate() {
    super.onFinishInflate()

  }

  fun getNavMenuClicks() = navView.clicks()

}