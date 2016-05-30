package com.mttnow.coolestprojects.app.dagger

import com.mttnow.coolestprojects.rx.AndroidThreadFactory
import com.mttnow.coolestprojects.rx.DefaultRxSchedulers
import com.mttnow.coolestprojects.rx.RxSchedulers
import dagger.Module
import dagger.Provides

@Module
class RxModule {

  @Provides
  fun threadFactory() = AndroidThreadFactory("RxJava-Thread")

  @AppScope
  @Provides
  fun rxSchedulers(threadFactory: AndroidThreadFactory): RxSchedulers = DefaultRxSchedulers(threadFactory)

}

