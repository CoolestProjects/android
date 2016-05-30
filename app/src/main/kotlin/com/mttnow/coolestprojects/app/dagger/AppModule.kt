package com.mttnow.coolestprojects.app.dagger

import android.content.Context
import com.mttnow.coolestprojects.R
import dagger.Module
import dagger.Provides
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class BaseServiceUrl

@Module
@AppScope
class AppModule(private val context: Context) {

  @Provides
  @AppScope
  fun context() = context.applicationContext

  @Provides
  @AppScope
  @BaseServiceUrl
  fun baseServiceUrl() = context.getString(R.string.base_api_url)
}