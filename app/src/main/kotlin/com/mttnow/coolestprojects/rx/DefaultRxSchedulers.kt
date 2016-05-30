package com.mttnow.coolestprojects.rx

import rx.schedulers.Schedulers
import java.util.concurrent.Executors

class DefaultRxSchedulers(val threadFactory: AndroidThreadFactory) : RxSchedulers {

  override fun mainThread() = Schedulers.immediate();

  override fun io() = Schedulers.from(Executors.newCachedThreadPool(threadFactory))

  override fun network() = Schedulers.from(Executors.newCachedThreadPool(threadFactory))

  override fun computation() = Schedulers.from(Executors.newCachedThreadPool(threadFactory))

}