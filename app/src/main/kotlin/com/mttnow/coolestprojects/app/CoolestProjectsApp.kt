package com.mttnow.coolestprojects.app

import android.app.Activity
import android.app.Application
import android.app.Service
import com.mttnow.coolestprojects.app.dagger.AppComponent
import com.mttnow.coolestprojects.app.dagger.AppModule
import com.mttnow.coolestprojects.app.dagger.DaggerAppComponent
import com.mttnow.coolestprojects.services.PreloadService
import net.danlew.android.joda.JodaTimeAndroid

class CoolestProjectsApp : Application() {

  companion object {
    @JvmStatic
    fun get(activity: Activity) = activity.application as CoolestProjectsApp
    @JvmStatic
    fun get(service: Service) = service.application as CoolestProjectsApp
  }

  lateinit var appComponent: AppComponent
    get

  override fun onCreate() {
    super.onCreate()

    JodaTimeAndroid.init(this)

    appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build()

    PreloadService.start(this)
  }

}
