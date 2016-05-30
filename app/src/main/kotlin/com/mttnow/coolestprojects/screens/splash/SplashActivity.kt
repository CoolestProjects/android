package com.mttnow.coolestprojects.screens.splash

import android.content.Intent
import android.os.Bundle
import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.screens.common.LifecyclePresenter
import com.mttnow.coolestprojects.screens.common.PresenterActivity
import com.mttnow.coolestprojects.screens.home.HomeActivity

class SplashActivity : PresenterActivity() {

  override fun onCreatePresenter(savedInstanceState: Bundle?): LifecyclePresenter? {
    super.onCreatePresenter(savedInstanceState)
    setContentView(R.layout.activity_splash)

    startActivity(Intent(this, HomeActivity::class.java))

    return null
  }
}
