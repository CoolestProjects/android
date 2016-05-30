package com.mttnow.coolestprojects.app.dagger

import android.content.Context
import com.google.gson.Gson
import com.mttnow.coolestprojects.network.CoolestProjectsService
import com.mttnow.coolestprojects.rx.RxSchedulers
import dagger.Component
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AppScope

@AppScope
@Component(modules = arrayOf(AppModule::class, NetworkModule::class, GsonModule::class, RxModule::class))
interface AppComponent {

  fun context(): Context

  fun coolestProjectsService(): CoolestProjectsService

  fun gson(): Gson

  fun rxSchedulers(): RxSchedulers

}
