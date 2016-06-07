package com.mttnow.coolestprojects.app

import android.app.Activity
import android.app.Application
import com.mttnow.coolestprojects.app.dagger.AppComponent
import com.mttnow.coolestprojects.app.dagger.AppModule
import com.mttnow.coolestprojects.app.dagger.DaggerAppComponent

class CoolestProjectsApp : Application() {

  companion object {
    @JvmStatic
    fun get(activity: Activity) = activity.application as CoolestProjectsApp
  }

  lateinit var appComponent: AppComponent
    get

  override fun onCreate() {
    super.onCreate()

    appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .build();
  }

}
