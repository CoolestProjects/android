package com.mttnow.coolestprojects.screens.common

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity

/**
 * Forwards on the lifecycle events to the presenter
 */
abstract class PresenterActivity : AppCompatActivity() {

  private var presenter : LifecyclePresenter? = null

  override final fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    presenter = onCreatePresenter(savedInstanceState)
    presenter?.onCreate()
  }

  @CallSuper
  open fun onCreatePresenter(savedInstanceState: Bundle?) : LifecyclePresenter? = null

  override fun onStart() {
    super.onStart()
    presenter?.onStart()
  }

  override fun onResume() {
    super.onResume()
    presenter?.onResume()
  }

  override fun onPause() {
    super.onPause()
    presenter?.onPause()
  }

  override fun onStop() {
    super.onStop()
    presenter?.onStop()
  }

  override fun onDestroy() {
    super.onDestroy()
    presenter?.onDestroy()
  }

}