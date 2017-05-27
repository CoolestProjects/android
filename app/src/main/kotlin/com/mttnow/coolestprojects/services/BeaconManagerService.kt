package com.mttnow.coolestprojects.services

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import com.estimote.sdk.Beacon
import com.estimote.sdk.BeaconManager
import com.estimote.sdk.Region
import com.mttnow.coolestprojects.app.CoolestProjectsApp
import com.mttnow.coolestprojects.models.BeaconDevice
import com.mttnow.coolestprojects.models.BeaconRegion
import com.mttnow.coolestprojects.network.CoolestProjectsService
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import javax.inject.Inject
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class BeaconManagerService : IntentService(BeaconManagerService::class.java.simpleName) {

    companion object {
        @JvmStatic
        fun start(context: Context) {
            context.startService(Intent(context, BeaconManagerService::class.java))
        }
    }

    val TAG: String = "BeaconManagerService"

    @Inject
    lateinit var coolestProjectsService: CoolestProjectsService
    lateinit var beaconManager: BeaconManager
    val beaconRegions: MutableMap<String, MutableSet<BeaconDevice>> = HashMap()

    override fun onCreate() {
        super.onCreate()

        DaggerBeaconManagerServiceComponent.builder()
                .appComponent(CoolestProjectsApp.get(this).appComponent)
                .build()
                .inject(this)

        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {

            Log.d(TAG, "Bluetooth Low Engery is available")

            beaconManager = BeaconManager(application)

            beaconManager.setMonitoringListener(
                    object : BeaconManager.MonitoringListener {
                        override fun onEnteredRegion(region: Region, list: List<Beacon>) {
                            Log.d(TAG, "Entered region $region")

                        }

                        override fun onExitedRegion(region: Region) {
                            Log.d(TAG, "Exit region $region")
                        }
                    })

            coolestProjectsService.messages()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        Log.d(TAG, "Messages download - $it")
                    }

            coolestProjectsService.regions()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        beaconManager.connect(
                                object : BeaconManager.ServiceReadyCallback {
                                    override fun onServiceReady() {
                                        startMonitoringRegions(it)
                                    }
                                }
                        )
                    }
        } else {
            Log.d(TAG, "Bluetooth Low Engery is not available")
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        coolestProjectsService.regions().subscribe {
            // TODO Stop monitoring beacons that have moved region or been removed
            // TODO Start monitoring new beacons or beacons that have moved region
        }
    }

    private fun startMonitoringRegions(regions: List<BeaconRegion>) {
        Log.d(TAG, "Start monitoring regions")
        regions.forEach { startMonitoringRegion(it) }
    }

    private fun startMonitoringRegion(region: BeaconRegion) {
        Log.d(TAG, "Start monitoring region $region")
        region.beacons.forEach { startMonitoringBeacon(region.regionId, it) }
    }

    private fun startMonitoringBeacon(regionId: String, beacon: BeaconDevice) {
        Log.d(TAG, "Start monitoring beacon $regionId,$beacon")
        beaconManager.startMonitoring(asEstimoteRegion(regionId, beacon))
        var beacons = beaconRegions[regionId]
        if (beacons == null) {
            beacons = HashSet<BeaconDevice>()
            beaconRegions[regionId] = beacons
        }
        beacons.add(beacon)
    }

    private fun stopMonitoring(regionId: String, beacon: BeaconDevice) {
        beaconManager.stopMonitoring(asEstimoteRegion(regionId, beacon))
        beaconRegions[regionId]?.remove(beacon)
        Log.d(TAG, "Stop monitoring beacon $regionId,$beacon")
    }

    private fun asEstimoteRegion(regionId: String, beacon: BeaconDevice) = Region(regionId, UUID.fromString(beacon.uuid), beacon.major, beacon.minor)
}