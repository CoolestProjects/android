package com.mttnow.coolestprojects.rx

import java.util.concurrent.ThreadFactory

class AndroidThreadFactory(private val threadName: String) : ThreadFactory {

  override fun newThread(r: Runnable): Thread {
    val thread = Thread(r, threadName)
    thread.priority = android.os.Process.THREAD_PRIORITY_BACKGROUND
    return thread
  }

}