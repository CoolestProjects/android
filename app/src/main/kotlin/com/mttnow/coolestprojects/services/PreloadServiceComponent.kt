package com.mttnow.coolestprojects.services

import com.mttnow.coolestprojects.app.dagger.AppComponent
import dagger.Component
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class PreloadScope

@PreloadScope
@Component(dependencies = arrayOf(AppComponent::class))
interface PreloadServiceComponent {

    fun inject(preloadService: PreloadService)
}