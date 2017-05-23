package com.mttnow.coolestprojects.screens.home

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.mttnow.coolestprojects.app.CoolestProjectsApp
import com.mttnow.coolestprojects.screens.common.LifecyclePresenter
import com.mttnow.coolestprojects.screens.common.PresenterActivity
import com.mttnow.coolestprojects.screens.home.dagger.DaggerHomeComponent
import com.mttnow.coolestprojects.screens.home.dagger.HomeModule
import com.mttnow.coolestprojects.screens.home.mvp.HomePresenter
import com.mttnow.coolestprojects.screens.home.mvp.HomeView
import javax.inject.Inject
import android.content.DialogInterface
import android.app.AlertDialog
import com.mttnow.coolestprojects.R
import com.mttnow.coolestprojects.services.BeaconManagerService


class HomeActivity : PresenterActivity() {

    val LOCATION_PERMISSION_REQUEST_ID: Int = 99

    @Inject
    lateinit var homeView: HomeView

    @Inject
    lateinit var hompresenter: HomePresenter

    fun checkLocationPermission(): Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                AlertDialog.Builder(this)
                        .setTitle(R.string.location_permission_request_title)
                        .setMessage(R.string.location_permission_request_message)
                        .setPositiveButton(android.R.string.ok, DialogInterface.OnClickListener { dialogInterface, i ->
                            //Prompt the user once explanation has been shown
                            ActivityCompat.requestPermissions(this@HomeActivity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_ID)
                        })
                        .create()
                        .show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), LOCATION_PERMISSION_REQUEST_ID)
            }
            return false
        } else {
            return true
        }
    }

    override fun onCreatePresenter(savedInstanceState: Bundle?): LifecyclePresenter? {
        super.onCreatePresenter(savedInstanceState)

        DaggerHomeComponent.builder()
                .homeModule(HomeModule(this))
                .appComponent(CoolestProjectsApp.get(this).appComponent)
                .build().intject(this)

        setContentView(homeView)
        return hompresenter
    }

    override fun onBackPressed() {
        if (homeView.onBackPressed()) {
            super.onBackPressed()
        }
    }

    override fun onResume() {
        super.onResume()
        if (checkLocationPermission()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission. ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
}
