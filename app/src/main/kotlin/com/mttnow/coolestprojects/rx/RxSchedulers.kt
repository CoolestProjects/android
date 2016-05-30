package com.mttnow.coolestprojects.rx

import rx.Scheduler


interface RxSchedulers {

  fun io(): Scheduler

  fun network(): Scheduler

  fun computation(): Scheduler

  fun mainThread(): Scheduler
}