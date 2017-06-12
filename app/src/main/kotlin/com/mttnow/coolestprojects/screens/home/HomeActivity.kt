package com.mttnow.coolestprojects.screens.home

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.screens.fragments.AboutFragment
import com.mttnow.coolestprojects.screens.fragments.HomeFragment
import com.mttnow.coolestprojects.screens.fragments.MapsFragment
import com.mttnow.coolestprojects.screens.fragments.ProjectsFragment
import com.mttnow.coolestprojects.screens.fragments.StagesFragment
import com.mttnow.coolestprojects.services.BeaconManagerService

private val FRAGMENT_TAG = "FRAGMNET_TAG"

class HomeActivity : AppCompatActivity() {

  val LOCATION_PERMISSION_REQUEST_ID: Int = 99
  private val bottomNav by lazy { findViewById(R.id.bottom_navigation) as BottomNavigationView }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    if (supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) == null) {
      swapFragment(HomeFragment())
    }

    listenForBottomNavEvents()
  }

  private fun listenForBottomNavEvents() {
    bottomNav.setOnNavigationItemSelectedListener { item ->
      when (item.itemId) {
        R.id.action_home -> swapFragment(HomeFragment())
        R.id.action_halls -> swapFragment(StagesFragment())
        R.id.action_maps -> swapFragment(MapsFragment())
        R.id.action_info -> swapFragment(ProjectsFragment())
        R.id.action_about -> swapFragment(AboutFragment())
      }
      true
    }
  }

  override fun onBackPressed() {
    if (supportFragmentManager.findFragmentByTag(FRAGMENT_TAG) !is HomeFragment) {
      swapFragment(HomeFragment())
    }
    super.onBackPressed()
  }

  override fun onResume() {
    super.onResume()
    if (checkLocationPermission()) {
      if (ContextCompat.checkSelfPermission(this,
          Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        BeaconManagerService.start(this)
      }
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    when (requestCode) {
      LOCATION_PERMISSION_REQUEST_ID -> {
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        }
      }
    }
  }

  private fun swapFragment(fragment: Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, fragment, FRAGMENT_TAG)
        .commit()
  }

  private fun checkLocationPermission(): Boolean {
    if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
      if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
        AlertDialog.Builder(this)
            .setTitle(R.string.location_permission_request_title)
            .setMessage(R.string.location_permission_request_message)
            .setPositiveButton(android.R.string.ok, { _, _ ->
              //Prompt the user once explanation has been shown
              ActivityCompat.requestPermissions(this@HomeActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                  LOCATION_PERMISSION_REQUEST_ID)
            })
            .create()
            .show()
      } else {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            LOCATION_PERMISSION_REQUEST_ID)
      }
      return false
    } else {
      return true
    }
  }
}
