package com.mttnow.coolestprojects.services

import com.mttnow.coolestprojects.app.dagger.AppComponent
import dagger.Component
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class BeaconManagerScope

@BeaconManagerScope
@Component(dependencies = arrayOf(AppComponent::class))
interface BeaconManagerServiceComponent {

    fun inject(service: BeaconManagerService)
}