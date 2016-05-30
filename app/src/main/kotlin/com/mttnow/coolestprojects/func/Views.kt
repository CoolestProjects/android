package com.mttnow.coolestprojects.func

import android.view.View


@Suppress("UNCHECKED_CAST")
fun <T : View> lazyView(viewFunction: () -> View) : Lazy<T> {
  return lazy { viewFunction()  as T }
}