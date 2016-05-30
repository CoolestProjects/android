package com.mttnow.coolestprojects.app.dagger

import com.google.gson.Gson
import dagger.Module
import dagger.Provides

@Module
class GsonModule {

  @Provides
  @AppScope
  fun gson() = Gson()
}